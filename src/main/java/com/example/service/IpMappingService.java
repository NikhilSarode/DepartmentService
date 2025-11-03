package com.example.service;

import com.couchbase.client.java.*;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import com.example.model.IpMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IpMappingService {

    private Cluster cluster;
    private Bucket bucket;
    private Scope scope;
    private Collection collection;

    @Value("${couchbase-url}")
    private String couchbaseUrl;

    @Value("${couchbase-username}")
    private String couchbaseUsername;

    @Value("${couchbase-password}")
    private String couchbasePassword;

    @Value("${couchbase-bucket}")
    private String couchbaseBucketName;

    @PostConstruct
    public void init() {
        try {
            System.out.println("Connecting to Couchbase...");

            // Connect to Couchbase cluster
            cluster = Cluster.connect(couchbaseUrl,
                    ClusterOptions.clusterOptions(couchbaseUsername, couchbasePassword));

            // Open bucket
            bucket = cluster.bucket(couchbaseBucketName);
            bucket.waitUntilReady(Duration.ofSeconds(10));

            // Open scope and collection
            scope = bucket.scope("reference_data");
            collection = scope.collection("geoip_data");

            System.out.println("Connected to Couchbase successfully!");
        } catch (Exception e) {
            System.err.println("Failed to connect to Couchbase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<IpMapping> findAll() {
        try {
            String query = "SELECT g.* FROM `config_data`.`reference_data`.`geoip_data` g";
            QueryResult result = cluster.query(query);

            List<IpMapping> mappings = result.rowsAsObject().stream()
                    .map(row -> {
                        IpMapping ip = new IpMapping();
                        ip.setFrom(row.getLong("from"));
                        ip.setTo(row.getLong("to"));
                        ip.setCountry(row.getString("country"));
                        ip.setState(row.getString("state"));
                        return ip;
                    })
                    .collect(Collectors.toList());

            System.out.println("Fetched " + mappings.size() + " records from Couchbase.");
            return mappings;

        } catch (Exception e) {
            System.err.println("Error fetching data from Couchbase: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public List<IpMapping> findByIp(long ip) {
        String query = String.format(
                "SELECT `from`, `to`, `country`, `state` FROM `config_data`.`reference_data`.`geoip_data` WHERE %d BETWEEN `from` AND `to`",
                ip
        );

        QueryResult result = cluster.query(query);

        List<IpMapping> mappings = result.rowsAsObject().stream()
                .map(row -> {
                    IpMapping ipMapping = new IpMapping();
                    ipMapping.setFrom(row.getLong("from"));
                    ipMapping.setTo(row.getLong("to"));
                    ipMapping.setCountry(row.getString("country"));
                    ipMapping.setState(row.getString("state"));
                    return ipMapping;
                })
                .collect(Collectors.toList());

        System.out.println("Fetched " + mappings.size() + " records from Couchbase for ip="+ip);

        return mappings;
    }
}
