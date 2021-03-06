package fi.otavanopisto.pyramus.plugin.googleoauth;

import java.util.HashMap;
import java.util.Map;

import fi.otavanopisto.pyramus.plugin.PluginDescriptor;

public class GoogleOauthPluginDescriptor implements PluginDescriptor {
  
  public Map<String, Class<?>> getBinaryRequestControllers() {
    return null;
  }

  public Map<String, Class<?>> getJSONRequestControllers() {
    return null;
  }
  
  public String getName() {
    return "Google-oauth";
  }
  
  public Map<String, Class<?>> getPageHookControllers() {
    return null;
  }
  
  public Map<String, Class<?>> getPageRequestControllers() {
    return null;
  }
  
  public Map<String, Class<?>> getAuthenticationProviders() {
    Map<String, Class<?>> authenticationProviders = new HashMap<>();
    
    authenticationProviders.put(this.getName(), GoogleOauthAuthorizationStrategy.class);
    
    return authenticationProviders;
  }
  
  public String getMessagesBundlePath() {
    return null;
  }
}
