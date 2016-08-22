# Jpeg Metadata Reader
Read jpeg metadata. This project contains a single concrete class "ExifReader" that can read a jpeg's exchange information. The class has one public overloaded search method, which performs the task of reading and storing the tags with values. To get the jpeg's tags, call either getTagsList or getTagsMap. The getTagsList return a String List of the tag values and the getTagsMap returns a String key, String value HashMap of the named tags plus their values.

<h2>Installation</h2>
<ol>
  <li>Download project's zip</li>
  <li>Compile project</li>
  <li>Generate jar library</li>
  <li>Add jar as external library</li>
</ol>

<h2>Dependencies</h2>
<ul>
  <li>Sanselan 0.97-incubator</li>
</ul>

<h2>Motivation</h2>
Worked a previous project, the program needed the ability to read a jpeg's metadata. I uploaded to Github as reference or possible Java library for others.

<h2>Notes</h2>
The ExifReader class reads a limited amount of tags - see ExifReader
