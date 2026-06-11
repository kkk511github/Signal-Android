package org.whispersystems.signalservice.internal.configuration;



import org.whispersystems.signalservice.api.push.TrustStore;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import okhttp3.ConnectionSpec;

public class SignalUrl {

  private final String                   url;
  private final Optional<String>         hostHeader;
  private final Optional<ConnectionSpec> connectionSpec;
  private       TrustStore               trustStore;

  public SignalUrl(String url, TrustStore trustStore) {
    this(url, null, trustStore, null);
  }

  public SignalUrl(String url, String hostHeader,
                   TrustStore trustStore,
                   ConnectionSpec connectionSpec)
  {
    this.url            = url;
    this.hostHeader     = Optional.ofNullable(hostHeader);
    this.trustStore     = trustStore;
    this.connectionSpec = Optional.ofNullable(connectionSpec);
  }


  public Optional<String> getHostHeader() {
    return hostHeader;
  }

  public String getUrl() {
    return url;
  }

  public TrustStore getTrustStore() {
    return trustStore;
  }

  public Optional<List<ConnectionSpec>> getConnectionSpecs() {
    return connectionSpec.isPresent() ? Optional.of(Collections.singletonList(connectionSpec.get())) : Optional.empty();
  }

  public List<ConnectionSpec> getDefaultConnectionSpecs() {
    return connectionSpec
        .map(Collections::singletonList)
        .orElseGet(() -> isCleartext() ? Collections.singletonList(ConnectionSpec.CLEARTEXT)
                                       : Collections.singletonList(ConnectionSpec.RESTRICTED_TLS));
  }

  public boolean isCleartext() {
    return url.regionMatches(true, 0, "http://", 0, "http://".length());
  }
}
