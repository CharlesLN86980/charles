/*
 Copyright (c) 2016, Mihai Emil Andronache
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.
 * Neither the name of charles nor the names of its
 contributors may be used to endorse or promote products derived from
 this software without specific prior written permission.
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.amihaiemil.charles;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Integration tests for {@link GraphCrawl}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
public class GraphCrawlITCase {
	private WebDriver driver;
	
	@Test
	public void crawlsAllPages() {
		GraphCrawl graph = new GraphCrawl("http://www.amihaiemil.com", this.driver);
		List<WebPage> pages = graph.crawl();
		Set<WebPage> uniquePages = new HashSet<WebPage>();
		for(WebPage p : pages) {
			System.out.println(p.getUrl().getLoc());
			assertTrue("Page crawled 2 times!", uniquePages.add(p));
		}
	}
	
	@Before
	public void initDriver() {
		this.driver = this.phantomJsDriver();
	}
	
	@After
	public void quitDriver() {
		this.driver.quit();
	}
	
    private WebDriver phantomJsDriver() {
    	String phantomJsExecPath =  System.getProperty("phantomjsExec");
        if("".equals(phantomJsExecPath)) {
            phantomJsExecPath = "/usr/local/bin/phantomjs";
        }

    	DesiredCapabilities dc = new DesiredCapabilities();
        dc.setJavascriptEnabled(true);
        dc.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            phantomJsExecPath
        );
        return new PhantomJSDriver(dc);
    }
}
