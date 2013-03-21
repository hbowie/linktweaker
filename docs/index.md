LinkTweaker User Guide
======================

<h2 id="toc">Table of Contents</h2>

<ul id="toc-div">
<li><a href="#introduction">Introduction</a></li>
<li><a href="#sysrqmts">System Requirements</a></li>
<li><a href="#rights">Rights</a></li>
<li><a href="#installation">Installation</a></li>
<li><a href="#gui">User Interface</a></li>
<li><a href="#input">Input</a></li>
<li><a href="#view">View</a></li>
<li><a href="#sort">Sort</a></li>
<li><a href="#filter">Filter</a></li>
<li><a href="#output">Output</a></li>
<li><a href="#template">Template</a></li>
<li><a href="#script">Script</a></li>
<li><a href="#logging">Logging</a></li>
<li><a href="#about">About</a></li>
<li><a href="#help-menu">Help Menu</a></li>
</ul>

<h2 id="introduction">Introduction</h2>

LinkTweaker adjusts URLs (aka hyperlinks) to make them both more useful and more usable. 

<p class="back-to-top"><a href="#toc">Back to Top</a></p>

<h2 id="sysrqmts">System Requirements</h2>

LinkTweaker is written in Java and can run on any reasonably modern operating system, including Mac OS X, Windows and Linux. LinkTweaker requires a Java Runtime Environment (JRE), also known as a Java Virtual Machine (JVM). The version of this JRE/JVM must be at least 6. Visit [www.java.com][java] to download a recent version for most operating systems. Installation happens a bit differently under Mac OS X, but generally will occur fairly automatically when you try to launch a Java app for the first time.  

<p class="back-to-top"><a href="#toc">Back to Top</a></p>

<h2 id="rights">Rights</h2>

LinkTweaker Copyright &copy; 2012 - 2013 Herb Bowie

LinkTweaker is [open source software][osd]. 

Licensed under the Apache License, Version 2.0 (the &#8220;License&#8221;); you may not use this file except in compliance with the License. You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an &#8220;AS IS&#8221; BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

LinkTweaker also incorporates or adapts the following open source software libraries. 

* BrowserLauncher2 &#8212; Copyright 2004 - 2007 Markus Gebhard, Jeff Chapman, used under the terms of the [GNU General Public License][gnu]. 

<p class="back-to-top"><a href="#toc">Back to Top</a></p>

<h2 id="installation">Installation</h2>

Download the latest version from [PowerSurgePub.com][downloads]. Decompress the downloaded file. Drag the resulting file or folder into the location where you normally store your applications. 

<p class="back-to-top"><a href="#toc">Back to Top</a></p>

<h2 id="gui">User Interface</h2>

LinkTweaker has a straightforward user interface consisting of the following elements. 

<dl>
	<dt>Input Link:</dt>
		<dd>Enter the link to be tweaked. Usually you would copy this from somewhere else, and then paste it into this field. </dd>
		
	<dt>Output Link:</dt>
		<dd>This is the resulting link, after the tweaking has occurred. It may or may not be different than the input, and it may or may not still perform the same function. Always test the link before relying on it.  </dd>
		
	<dt>Remove SharePoint Cruft?</dt>
		<dd>When copying a link from Internet Explorer while navigating a SharePoint site, the copied link often seems to contain a great deal of additional, optional and apparently quite useless parameters. Checking this box will cause LinkTweaker to try to remove this stuff, resulting in a shorter and more straightforward hyperlink. </dd>
		
	<dt>Insert Redirect?</dt>
		<dd>In some cases, especially while inside a corporate firewall, it may be necessary to insert some sort of redirecting URL in order to successfully navigate to your ultimate destination. You will need to enter your redirect URL in the Preferences pane. Once doing so, checking this box will cause the redirect to be inserted at the front of the URL.  </dd>
		
	<dt>Show spaces as spaces?</dt>
		<dd>In order to avoid corruption, especially when being relayed via email, spaces in hyperlinks are often converted to "%20" strings. Checking this box will convert these strings back to spaces, which will generally make the link more readable (but less durable). This may be useful as a temporary setting, just to see the elements of your URL as they would normally appear in a file or web browser.   </dd>
		
	<dt>Tweak</dt>
		<dd>Pressing this button will cause the tweaking logic to be applied to the Input Link, producing a new Output Link. However, since this operation is also triggered by most of your other actions in the user interface, it may not be strictly necessary to press this button.   </dd>
		
	<dt>Launch</dt>
		<dd>Pressing this button will cause the Output Link to be launched, using your favorite Web browser. This should be done to test the Output Link, to ensure it actually takes the user to your desired location.  </dd>
		
	<dt>Copy</dt>
		<dd>Pressing this button will cause the Output Link to be copied to the system scratchpad, so that your next paste will place the Output Link in a new location.  </dd>
</dl>

<p class="back-to-top"><a href="#toc">Back to Top</a></p>

<h2 id="about">About</h2>

This tab allows the user to view some basic information about the LinkTweaker program. It includes a version number and copyright information. More complete information is contained in this user guide.

This tab can also be reached by selecting the About LinkTweaker Menu Item. On the Mac, this will appear in the Application Menu. On other operating systems, it will appear in the Help Menu.

<p class="back-to-top"><a href="#toc">Back to Top</a></p>

<h2 id="help-menu">Help Menu</h2>

The Help Menu includes three other useful items. The first, User Guide, attempts to launch the LinkTweaker User Guide installed with your program as a window within a local Web Browser. This operation will not always be successful, due to operating system restrictions. In some cases, as an alternative, your Web Browser will be directed to the LinkTweaker Home Page on the Web. It is possible that the information on the Web may not apply to your version of LinkTweaker. You can always navigate to your local user guide by using your file system navigator to select the file &#8220;userguide/index.html&#8221; within your LinkTweaker application folder, and then launching it using your preferred Web Browser. 

The next item on the Help Menu will attempt to direct your Web Browser to the LinkTweaker Home Page. You will need an active Internet connection for this to be successful. Again, operating system restrictions may prevent this action from being completed successfully. If so, you can always copy and paste the following address into your Web Browser: http://www.powersurgepub.com. 

[java]:  http://www.java.com/

[pspub]:     http://www.powersurgepub.com/
[downloads]: http://www.powersurgepub.com/downloads.html
[store]:     http://www.powersurgepub.com/store.html

[markdown]:  http://daringfireball.net/projects/markdown/
[pegdown]:   https://github.com/sirthias/pegdown/blob/master/LICENSE
[parboiled]: https://github.com/sirthias/parboiled/blob/master/LICENSE
[Mathias]:   https://github.com/sirthias

[club]:         clubplanner.html
[filedir]:      filedir.html
[metamarkdown]: metamarkdown.html
[template]:     template.html

[osd]:				http://opensource.org/osd
[gnu]:        http://www.gnu.org/licenses/
[apache]:			http://www.apache.org/licenses/LICENSE-2.0.html

<p class="back-to-top"><a href="#toc">Back to Top</a></p>