package library.utils.rss;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import library.utils.rss.model.RssObject;

@SuppressWarnings("unused")
public class Rss {

    public Rss() {}

    public List<RssObject> parseRssXml(String data) throws XmlPullParserException, IOException {

        List<RssObject> listResult = new ArrayList<>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();

        // We will get the XML from an input stream
        xpp.setInput(new StringReader(data));
        boolean insideItem = false;
        // Returns the type of current event: START_TAG, END_TAG, etc..
        int eventType = xpp.getEventType();
        RssObject rssObject = new RssObject();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = true;
                } else if (insideItem) {
                    setElement(xpp, rssObject);

                    if (rssObject.isCompleted()) {
                        listResult.add(new RssObject(rssObject));
                        rssObject.clear();
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                insideItem = false;
            }

            eventType = xpp.next();
        }

        return listResult;
    }

    private void setElement(XmlPullParser xpp, RssObject rssObject) throws XmlPullParserException, IOException {
        switch (xpp.getName().toLowerCase()) {
            case "title": rssObject.setTitle(xpp.nextText());
                break;
            case "link": rssObject.setLink(xpp.nextText());
                break;
            case "comments": rssObject.setComments(xpp.nextText());
                break;
            case "pubdate": rssObject.setPubDate(xpp.nextText());
                break;
            case "description": rssObject.setDescription(xpp.nextText());
                break;
        }
    }
}
