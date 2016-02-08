package com.jovi.rronline;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawler {
	
	  private static Pattern patternDomainName;
	  private Matcher matcher;
	  private static final String DOMAIN_NAME_PATTERN 
		= "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
	  static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	  }
	
	 Set<String> getDataFromGoogle(String query) {
			
			Set<String> result = new HashSet<String>();	
			String request = "https://scholar.google.com/citations?view_op=search_authors&mauthors=" + query;
			
			System.out.println("Sending request..." + request);
				
			try {

				// need http protocol, set this as a Google bot agent :)
				Document doc = Jsoup
					.connect(request)
					.userAgent(
					  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					.timeout(5000).get();

				// get all links
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String domainName = "";
					
					String temp = link.attr("href");		
					
					if(temp.startsWith("/url?q=")){
		                                //use regex to get domain name
						matcher = patternDomainName.matcher(temp);
						if (matcher.find()) {
							domainName = matcher.group(0).toLowerCase().trim();
						}
						
						result.add(domainName);
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
				
			return result;
		  }
}
