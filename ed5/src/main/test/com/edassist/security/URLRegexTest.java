/**
 * 
 */
package com.edassist.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 *
 */
public class URLRegexTest {

	static final String URL_ACTION_REGEX = "\\S+(action=updateParticipant)\\S+";
	static final String URL_BASE_REGEX = "\\S+(/TAMSDesktop[/]?)";
	static final String URL_CSS_REGEX = "\\S+(jpg|png|gif|css)$";

	@Test
	public final void testURLActionRegex() {

		String url = "http://localhost:8701/TAMS4Web/appmanager/TAMSPortal/TAMSDesktop?_nfpb=true&_st=e0FFU31haEkybko3UFdPMDVIKzVVOFVNRXM0MkdNTXBQcTh5M2lTbFVWN211QU85NVppMXU5Q0FHT1ZaQ1JCek1sYVl0&_windowLabel=T6601662751289243743517&_urlType=render&_pageLabel=P1600262751289243575225&wlpT6601662751289243743517_action=updateParticipant&wlpT6601662751289243743517_participantId=251135";

		Pattern pattern = Pattern.compile(URL_ACTION_REGEX);
		Matcher matcher = pattern.matcher(url);

		Assert.assertTrue(matcher.matches());
	}

	@Test
	public final void testBaseURLIgnoreParametersRegex() {

		String url = "http://localhost:8701/TAMS4Web/appmanager/TAMSPortal/TAMSDesktop?_nfpb=true&_st=e0FFU31haEkybko3UFdPMDVIKzVVOFVNRXM0MkdNTXBQcTh5M2lTbFVWN211QU85NVppMXU5Q0FHT1ZaQ1JCek1sYVl0&_windowLabel=T6601662751289243743517&_urlType=render&_pageLabel=P1600262751289243575225&wlpT6601662751289243743517_action=updateParticipant&wlpT6601662751289243743517_participantId=251135";

		Pattern pattern = Pattern.compile(URL_BASE_REGEX);
		Matcher matcher = pattern.matcher(url);

		Assert.assertFalse(matcher.matches());
	}

	@Test
	public final void testBaseURLMatchNoSlashRegex() {

		String url = "http://localhost:8701/TAMS4Web/appmanager/TAMSPortal/TAMSDesktop";

		Pattern pattern = Pattern.compile(URL_BASE_REGEX);
		Matcher matcher = pattern.matcher(url);

		Assert.assertTrue(matcher.matches());
	}

	@Test
	public final void testBaseURLMatchSlashRegex() {

		String url = "http://localhost:8701/TAMS4Web/appmanager/TAMSPortal/TAMSDesktop/";

		Pattern pattern = Pattern.compile(URL_BASE_REGEX);
		Matcher matcher = pattern.matcher(url);

		Assert.assertTrue(matcher.matches());
	}

	@Test
	public final void testCSSURLMatchAllRegex() {

		String url = "http://localhost:8701/TAMS4Web/framework/skeletons/TAMS/css/tams.css";

		Pattern pattern = Pattern.compile(URL_CSS_REGEX);
		Matcher matcher = pattern.matcher(url);

		Assert.assertTrue(matcher.matches());
	}

	@Test
	public final void testCSSURLNoMatchAllRegex() {

		String url = "http://localhost:8701/TAMS4Web/framework/skeletons/TAMS/css/tams.cssfe";

		Pattern pattern = Pattern.compile(URL_CSS_REGEX);
		Matcher matcher = pattern.matcher(url);

		Assert.assertFalse(matcher.matches());
	}
}
