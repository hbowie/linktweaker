/*
 * Copyright 2012 - 2015 Herb Bowie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.powersurgepub.linktweaker;

/**
 A main program that will use LinkTweaker as an auxiliary window. 

 @author Herb Bowie
 */
public interface LinkTweakerApp {
  
  /**
   Get the current link so that it can be tweaked. 
  
   @return The Link to be tweaked. 
  */
  public String getLinkToTweak();
  
  /**
   Set a link field to a new value after it has been tweaked. 
  
   @param tweakedLink The link after it has been tweaked. 
   @param linkID      A string identifying the link, in case there are more
                      than one. This would be the text used in the label
                      for the link. 
  */
  public void putTweakedLink (String tweakedLink, String linkID);
  
}


