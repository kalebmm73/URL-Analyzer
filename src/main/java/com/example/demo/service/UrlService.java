package com.example.demo.service;
import com.example.demo.domain.CertificateInfoDTO;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Url;
import com.example.demo.domain.UrlAnalysisResult;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

@AllArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final CustomerRepository customerRepository;

    //set url to customer
    public Url create(Url url, String userId) {
        //set customer object to database user matching userId
        Customer customer = customerRepository.findByUserId(userId).orElse(null);
        //if user exists
        if (customer != null) {
            //set url to current customer object
            url.setCustomer(customer);
        }

        //console validation
        System.out.println("Found customer in database: " + userId);
        System.out.println("Created url for customer_id: " + customer.getCustomer_id());
        //save url to database
        return urlRepository.save(url);
    }

    public void delete(Long id) {
        urlRepository.deleteById(id);
    }

    public void update(Long id, String url) {
        Url urlToEdit = urlRepository.findById(id).orElse(null);
        urlToEdit.setUrl(url);
        urlRepository.save(urlToEdit);
        System.out.println("Updated url for urlId: " + id);
        System.out.println("Updated url to: " + url);
    }

    public List<Url> findAll(String userId) {
        Customer customer = customerRepository.findByUserId(userId).orElse(null);
        //System.out.println("UrlService > findAllByuserId : " + userId);
        if (customer != null) {
            return urlRepository.findAllUrlsByCustomer(customer);
        }
        System.out.println("Returning all urls from: " + userId);
        return null;
    }

    public UrlAnalysisResult analyze(String urlString)
    {
        try
        {
            System.out.println("Attempting to analyze url: " + urlString);
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            String cipherSuite = connection.getCipherSuite();
            Map<String, String> headers = new LinkedHashMap<>();
            for (Map.Entry<String, java.util.List<String>> entry : connection.getHeaderFields().entrySet())
            {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().isEmpty())
                {
                    headers.put(entry.getKey(), entry.getValue().get(0));
                }
            }
            List<CertificateInfoDTO> certificates = new ArrayList<>();
            Certificate[] certs = connection.getServerCertificates();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Certificate cert : certs)
            {
                if (cert instanceof X509Certificate x509)
                {
                    CertificateInfoDTO certDTO = new CertificateInfoDTO(
                            x509.getSubjectX500Principal().getName(),
                            x509.getIssuerX500Principal().getName(),
                            sdf.format(x509.getNotBefore()),
                            sdf.format(x509.getNotAfter()),
                            x509.getSerialNumber().toString(),
                            x509.getSigAlgName()
                    );
                    certificates.add(certDTO);
                }
            }

            //new code 113-114
            String host = url.getHost();
            int port = (url.getPort() == -1) ? 443 : url.getPort(); // Default to 443 if not specified

// Get supported protocols and ciphers

            //new code 119-121
            Map<String, List<String>> sslInfo = getSupportedSslProtocolsAndCiphers(host, port);
            List<String> supportedProtocols = sslInfo.get("protocols");
            List<String> supportedCiphers = sslInfo.get("ciphers");

            connection.disconnect();
            System.out.println("Url analyzed successfully");

            //change next line to include supportedProtocols, supportCiphers
            UrlAnalysisResult uar = new UrlAnalysisResult(String.valueOf(responseCode),
                    cipherSuite, headers, certificates, supportedProtocols, supportedCiphers);
            //new code 130-131
            uar.setSupportedProtocols(supportedProtocols);
            uar.setSupportedCiphers(supportedCiphers);

            System.out.println(uar.toString());
            return uar;

        }
        catch (IOException | RuntimeException e)
        {
            throw new RuntimeException("Error analyzing URL: " + e.getMessage());
        }
    }

    //new code from 144-164
    private Map<String, List<String>> getSupportedSslProtocolsAndCiphers(String host, int port) {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);

            SSLSocketFactory factory = context.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

            // Don't need to connect the socket, just inspect its capabilities
            String[] supportedProtocols = socket.getSupportedProtocols();
            String[] supportedCiphers = socket.getSupportedCipherSuites();
            socket.close();

            Map<String, List<String>> result = new LinkedHashMap<>();
            result.put("protocols", List.of(supportedProtocols));
            result.put("ciphers", List.of(supportedCiphers));
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch supported SSL info: " + e.getMessage());
        }
    }
}