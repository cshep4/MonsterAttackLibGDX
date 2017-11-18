package com.cshep4.monsterattack.game;

//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;
   
  
public class XMLPullParserHandler {  
//    private ArrayList<HighScore> highScores = new ArrayList<HighScore>();
//    private HighScore highScore;
//    private ArrayList<Wave> waves = new ArrayList<Wave>();
//    private Wave wave;
//    private String text;
//
//    public ArrayList<HighScore> getHighScores() {
//        return highScores;
//    }
//
//    public ArrayList<HighScore> parseHighScores(InputStream is) {
//           try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            factory.setNamespaceAware(true);
//            XmlPullParser  parser = factory.newPullParser();
//
//            parser.setInput(is, null);
//
//            int eventType = parser.getEventType();
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String tagname = parser.getName();
//                switch (eventType) {
//                case XmlPullParser.START_TAG:
//                    if (tagname.equalsIgnoreCase("score")) {
//                        // create a new instance of Block
//                        highScore = new HighScore();
//                    }
//                    break;
//
//                case XmlPullParser.TEXT:
//                    text = parser.getText();
//                    break;
//
//                case XmlPullParser.END_TAG:
//                    if (tagname.equalsIgnoreCase("score")) {
//                        // add Block object to list
//                    	highScores.add(highScore);
//                    }  else if (tagname.equalsIgnoreCase("id")) {
//                    	highScore.setPlace(Integer.parseInt(text));
//                    }  else if (tagname.equalsIgnoreCase("kills")) {
//                    	highScore.setNumkills(Integer.parseInt(text));
//                    }  else if (tagname.equalsIgnoreCase("name")) {
//                    	highScore.setName(text);
//	                }
//                    break;
//
//                default:
//                    break;
//                }
//                eventType = parser.next();
//            }
//
//        } catch (XmlPullParserException e) {e.printStackTrace();}
//        catch (IOException e) {e.printStackTrace();}
//
//        return highScores;
//    }
//
//    public ArrayList<Wave> parseWaves(InputStream is) {
//        try {
//         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//         factory.setNamespaceAware(true);
//         XmlPullParser  parser = factory.newPullParser();
//
//         parser.setInput(is, null);
//
//         int eventType = parser.getEventType();
//         while (eventType != XmlPullParser.END_DOCUMENT) {
//             String tagname = parser.getName();
//             switch (eventType) {
//             case XmlPullParser.START_TAG:
//                 if (tagname.equalsIgnoreCase("wave")) {
//                     // create a new instance of Block
//                     wave = new Wave();
//                 }
//                 break;
//
//             case XmlPullParser.TEXT:
//                 text = parser.getText();
//                 break;
//
//             case XmlPullParser.END_TAG:
//                 if (tagname.equalsIgnoreCase("wave")) {
//                     // add Block object to list
//                     waves.add(wave);
//                 } else if (tagname.equalsIgnoreCase("id")) {
//                 	 wave.setId(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("s1")) {
//                	 wave.setS1(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("s2")) {
//                	 wave.setS2(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("s3")) {
//                	 wave.setS3(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("s4")) {
//                	 wave.setS4(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("b1")) {
//                	 wave.setB1(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("b2")) {
//                	 wave.setB2(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("b3")) {
//                	 wave.setB3(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("b4")) {
//                	 wave.setB4(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("s1mutate")) {
//                	 wave.setS1Mutate(Boolean.parseBoolean(text));
//                 } else if (tagname.equalsIgnoreCase("s2mutate")) {
//                	 wave.setS2Mutate(Boolean.parseBoolean(text));
//                 } else if (tagname.equalsIgnoreCase("s3mutate")) {
//                	 wave.setS3Mutate(Boolean.parseBoolean(text));
//                 } else if (tagname.equalsIgnoreCase("sProducer")) {
//                	 wave.setSProducer(Integer.parseInt(text));
//                 } else if (tagname.equalsIgnoreCase("b1mutate")) {
//                	 wave.setB1Mutate(Boolean.parseBoolean(text));
//                 } else if (tagname.equalsIgnoreCase("b2mutate")) {
//                	 wave.setB2Mutate(Boolean.parseBoolean(text));
//                 } else if (tagname.equalsIgnoreCase("b3mutate")) {
//                	 wave.setB3Mutate(Boolean.parseBoolean(text));
//                 } else if (tagname.equalsIgnoreCase("bProducer")) {
//                	 wave.setBProducer(Integer.parseInt(text));
//                 }
//                 break;
//
//             default:
//                 break;
//             }
//             eventType = parser.next();
//         }
//
//     } catch (XmlPullParserException e) {e.printStackTrace();}
//     catch (IOException e) {e.printStackTrace();}
//
//     return waves;
// }
}  
