HOW TO INSTALL:

Java 8 required

Any Maven supporting IDE is needed, I used IntelliJ Idea

Open the project in IDE, and it should automatically install the dependencies required from pom.xml

Run the 'JettyServer' class in "java code" and also specify the path of the lucene_index which I will send IN command line arguments(or hardcode if needed)

CORS browser plugin should be installed on the pc where it will run (https://chrome.google.com/webstore/detail/allow-cors-access-control/lhobafahddgcelffkeicbaginigeejlf/related?hl=en)

Run the index.html in "web" file from web folder; It should be able to query the Jetty Server;
Enter your search query in page

Sample Test Cases -
'aamir', 'football'


Links in "java code\hinduscrap\res"

----------

WHAT I DID

The task was accomplished in 3 stages:

1. Data Scraping from The Hindu
2. Indexing the data in Lucene
3. WebUi to make the query the results



1.
A.) There are 365 days in 2010, and each day has close to 40 articles.
B.) This made it tough to download all the data as there are almost 5000 urls
C.) I have extracted all the URLS from daily pages into a CSV file, and then started scraping data from the articles of each day


I wrote a basic Java code to generate all the 365 url matching the pattern of Hindu.
Then I ran a Selenium code which was able to get all the articles URLs from the daily pages. (With 'ece' at end)
This takes the popular articles also into its account, which are not of 2010 but latest ones. (Could be solved by more appropriate Selenium extraction)

Now, I have the links of all articles in 2010 in csv file.

Now I use HTTrack to get the webHtml of the links in the CSV. (Manual Work)


2.
After this, I write Java based Lucene code which will index the above data from the html files.
The schema is as {title:TEXT, content:TEXT} and StandardAnalyser is used.

The lucene indexes are saved as such and can be reused again.

3.

Web Ui consists of HTML and AngularJS
Backend Server consists of Java based Jetty Server which handles the GET Requests
The saved lucene index is used here for querying.


