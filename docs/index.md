<!-- Generated using template product-user-guide-template.mdtoc -->
<!-- Generated using template product-user-guide-template.md -->
<h1 id="link-tweaker-user-guide">Link Tweaker User Guide</h1>


<h2 id="table-of-contents">Table of Contents</h2>

<div id="toc">
  <ul>
    <li>
      <a href="#introduction">Introduction</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li>
          <a href="#system-requirements">System Requirements</a>
        </li>
        <li>
          <a href="#rights">Rights</a>
        </li>
        <li>
          <a href="#installation">Installation</a>
        </li>
      </ul>

    </li>
    <li>
      <a href="#data-fields">Data Fields</a>
    </li>
    <li>
      <a href="#user-interface">User Interface</a>
      <ul>
        <li>
          <a href="#the-tool-bar">The Tool Bar</a>
        </li>
      </ul>

    </li>
  </ul>

</div>


<h2 id="introduction">Introduction</h2>


Link Tweaker is a modest little application that adjusts URLs (aka hyperlinks) to make them more useful and more usable. It converts over-tokenized URLs into more readable strings. It can also optionally remove various forms of SharePoint detritus. It can also optionally insert a user-defined redirection string in front of a URL.

LinkTweaker's operation is based on the philosophy that URLs should point a Web browser to a specific address, but should also tell a human user where a resource is located, so that said user can navigate to that resource through other means as well. To this end, LinkTweaker converts as many escape sequences as practical back to their original characters (changing a "%2F" back to a simple slash, for example), as well as removing other cruft and detritus that may have attached itself to a link somewhere along its journey. 


<h2 id="getting-started">Getting Started</h2>


<h3 id="system-requirements">System Requirements</h3>


Link Tweaker is written in Java and can run on any reasonably modern operating system, including Mac OS X, Windows and Linux. Link Tweaker requires a Java Runtime Environment (JRE), also known as a Java Virtual Machine (JVM). The version of this JRE/JVM must be at least 6. Visit [www.java.com](http://www.java.com) to download a recent version for most operating systems. Installation happens a bit differently under Mac OS X, but generally will occur fairly automatically when you try to launch a Java app for the first time.

Because Link Tweaker may be run on multiple platforms, it may look slightly different on different operating systems, and will obey slightly different conventions (using the CMD key on a Mac, vs. an ALT key on a PC, for example).

<h3 id="rights">Rights</h3>


Link Tweaker Copyright 2012 - 2014 Herb Bowie

Link Tweaker is [open source software](http://opensource.org/osd). Source code is available at [GitHub](http://github.com/hbowie/linktweaker).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

  [www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.


<h3 id="installation">Installation</h3>


Download the latest version from [PowerSurgePub.com](http://www.powersurgepub.com/downloads.html). Decompress the downloaded file. Drag the resulting file or folder into the location where you normally store your applications. Double-click on the jar file (or the application, if you've downloaded the Mac app) to launch.


<h2 id="data-fields">Data Fields</h2>


Link Tweaker works with links (aka hyperlinks, aka URLs). It provides for an input link, and then a separate text box for the output link, after one or more possible transformations.

<h2 id="user-interface">User Interface</h2>



<h3 id="the-tool-bar">The Tool Bar</h3>


A toolbar with multiple buttons appears at the top of the user interface.

* **OK** -- Indicates that you have completed adding/editing the fields for the current .
* **+** -- Clear the data fields and prepare to add a new  to the collection.
* **-** -- Delete the current .
* **&lt;&lt;** -- Display the first  in the collection.
* **&lt;** -- Display the next  in the collection.
* **&gt;** -- Display the next  in the collection.
* **&gt;&gt;** -- Display the last  in the collection.
* **Launch** -- Launch the current  in your Web browser. (This may also be accomplished by clicking the arrow that appears just to the left of the URL itself.)
* **Find** -- Looks for the text entered in the field just to the left of this button, and displays the first  containing this text in any field, ignoring case. After finding the first occurrence, this button's text changes to **Again**, to allow you to search again for the next  containing the specified text.

LinkTweaker has a single main window containing the following controls.

Input Link
:    Enter the link to be tweaked. Usually you would copy this information from a field in another app, and then paste it into this field.

Output Link
:    This is the resulting link, after the tweaking has occurred. It may or may not be different than the input, and it may or may not still perform the same function. Always test the link before relying on it.

Remove SharePoint Cruft?
:    When copying a link from Internet Explorer while navigating a SharePoint site, the copied link often seems to contain a great deal of additional, optional and apparently quite useless information. Checking this box will cause LinkTweaker to try to remove this stuff, resulting in a shorter and more straightforward hyperlink.

Insert Redirect?
:    In some cases, especially while inside a corporate firewall, it may be necessary to insert some sort of redirecting URL in order to successfully navigate to your ultimate destination. You will need to enter your redirect URL in the Preferences pane. Once doing so, checking this box will cause the redirect to be inserted before the Input Link.

Show spaces as spaces?
:    In order to avoid corruption, especially when being relayed via email, spaces in hyperlinks are often converted to "%20" strings. Checking this box will convert these strings back to spaces, which will generally make the link more readable (but less durable). This may be useful as a temporary setting, just to see the elements of your URL as they would normally appear in a file or web browser.

Tweak
:    Pressing this button will cause the tweaking logic to be applied to the Input Link, producing a new Output Link. However, since this operation is also triggered by most of your other actions in the user interface, it may not be strictly necessary to press this button.

Launch
:    Pressing this button will cause the Output Link to be launched, using your favorite Web browser. This should be done to test the Output Link, to ensure it actually takes the user to your desired location.

Copy
:    Pressing this button will cause the Output Link to be copied to the system clipboard, so that your next paste will place the Output Link into a new location.





[java]:       http://www.java.com/
[pspub]:      http://www.powersurgepub.com/
[downloads]:  http://www.powersurgepub.com/downloads.html
[osd]:		  http://opensource.org/osd
[gnu]:        http://www.gnu.org/licenses/
[apache]:	     http://www.apache.org/licenses/LICENSE-2.0.html
[markdown]:		http://daringfireball.net/projects/markdown/
[multimarkdown]:  http://fletcher.github.com/peg-multimarkdown/

[wikiq]:     http://www.wikiquote.org
[support]:   mailto:support@powersurgepub.com
[fortune]:   http://en.wikipedia.org/wiki/Fortune_(Unix)
[opml]:      http://en.wikipedia.org/wiki/OPML
[textile]:   http://en.wikipedia.org/wiki/Textile_(markup_language)
[pw]:        http://www.portablewisdom.org

[store]:     http://www.powersurgepub.com/store.html

[pegdown]:   https://github.com/sirthias/pegdown/blob/master/LICENSE
[parboiled]: https://github.com/sirthias/parboiled/blob/master/LICENSE
[Mathias]:   https://github.com/sirthias

[club]:         clubplanner.html
[filedir]:      filedir.html
[metamarkdown]: metamarkdown.html
[template]:     template.html

[mozilla]:    http://www.mozilla.org/MPL/2.0/


