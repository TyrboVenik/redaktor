import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by venik on 17.11.16.
 */
public class CommandLine {
    public static ArrayList<Integer> integers;
    static{
        integers = new ArrayList<>();
    }

    public static MyPoint getMyPoint(){
        if (integers.size()>=2){
         MyPoint MyPoint = new MyPoint(integers.get(0),integers.get(1));
            integers.remove(0);
            integers.remove(0);
            return MyPoint;
        }
        return null;
    }

    public static int getValue(){
        if (integers.size()>=1){
            int value =  integers.get(0);
            integers.remove(0);
            return value;
        }
        return -1;
    }

    public static void doAction(String param) {


        try{

            param = param.trim();


            if(param.startsWith("section")){
                param = param.substring("section".length(),param.length());
                param = param.trim();

                String source;
                String prefix;

                if(param.indexOf(' ') != -1) {
                    source = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else {
                    Interface.consolePrintln("Incorrect parametrs");
                    return;
                }
                param = param.trim();

                if(param.indexOf(' ') != -1) {
                    prefix = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else{
                    Interface.consolePrintln("Incorrect parametrs");
                    return;
                }
                param.trim();

                ArrayList<Integer> integers1 = new ArrayList<>();

                if (param.matches("( )*\\d+( )*\\d+( )*\\d+( )*\\d+")) {
                    Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
                    Matcher matcher = pattern.matcher(param);
                    while (matcher.find()) {
                        String s = matcher.group(0);
                        if (s.replaceAll("\\D", "").length() == s.length()) {
                            integers1.add(Integer.parseInt(s));
                        }
                    }
                }

                if (integers1.size()<4) {
                    Interface.consolePrintln("Incorrect parametrs");
                    return;
                }
                GraphicsMain.section(source,prefix,
                        new MyPoint(integers1.get(0),integers1.get(1)),
                        new MyPoint(integers1.get(2),integers1.get(3)));

            }

            if(param.startsWith("group")){
                param = param.substring("group".length(),param.length());
                param = param.trim();
                ArrayList<String> nameList = new ArrayList<>();

                String groupName = null;

                if(param.indexOf(' ') != -1) {
                    groupName = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    Interface.consolePrintln("Incorrect group name");

                while (!param.startsWith("end")){
                    if (param.indexOf(' ') == -1){
                        Interface.consolePrintln("Incorrect parametrs");
                    }
                    String name = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                    param = param.trim();
                    System.out.println(name);
                    nameList.add(name);
                }

                if(param.startsWith("end")){
                    GraphicsMain.makeGroup(groupName,nameList);
                    return;
                }
                Interface.consolePrintln("Incorrect parametrs");
                return;
            }

            if(param.startsWith("projection")){
                param = param.substring("projection".length(),param.length());
                param = param.trim();

                String source = new String();
                String prefix = new String();

                if(param.indexOf(' ') != -1) {
                    source = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    Interface.consolePrintln("Incorrect parametrs");

                param = param.trim();

                if(param.indexOf(' ') != -1) {
                    prefix = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    Interface.consolePrintln("Incorrect parametrs");

                param.trim();
                ArrayList<Integer> integers1 = new ArrayList<>();

                if (param.matches("( )*\\d+( )*\\d+( )*\\d+( )*\\d+")) {
                    Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
                    Matcher matcher = pattern.matcher(param);
                    while (matcher.find()) {
                        String s = matcher.group(0);
                        if (s.replaceAll("\\D", "").length() == s.length()) {
                            integers1.add(Integer.parseInt(s));
                        }
                    }
                }

                if(integers1.size()<4) {
                    Interface.consolePrintln("Incorrect parametrs");
                    return;
                }
                GraphicsMain.projection(source,prefix,
                        new MyPoint(integers1.get(0),integers1.get(1)),
                        new MyPoint(integers1.get(2),integers1.get(3)));

            }

            if (param.startsWith("change")) {
                param = param.substring("change".length(),param.length());

                param = param.trim();

                String name = null;
                if(param.indexOf(' ') != -1) {
                    name = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    Interface.consolePrintln("Incorrect parametrs");

                param = param.trim();

                ArrayList<Integer> arr = new ArrayList<>();

                if (param.matches("\\d+( )+\\d+( )+\\d+") || param.matches("\\d+( )+\\d+")) {
                    Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
                    Matcher matcher = pattern.matcher(param);
                    while (matcher.find()) {
                        String s = matcher.group(0);
                        if (s.replaceAll("\\D", "").length() == s.length()) {
                            arr.add(Integer.parseInt(s));
                        }
                    }
                }

                if(arr.size() < 2){
                    Interface.consolePrintln("Incorrect number parametrs");
                    return;
                }

                GraphicsMain.change(name,arr);
                return;
            }



            if (param.startsWith("clipping")) {
                param = param.substring("clipping".length(),param.length());
                param = param.trim();

                ArrayList<Integer> arr = new ArrayList<>();

                if (param.matches("\\d+( )+\\d+( )+\\d+( )+\\d+( )+\\d+( )+\\d+")) {
                    Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
                    Matcher matcher = pattern.matcher(param);
                    while (matcher.find()) {
                        String s = matcher.group(0);
                        if (s.replaceAll("\\D", "").length() == s.length()) {
                            arr.add(Integer.parseInt(s));
                        }
                    }
                }
                if(arr.size()<5){
                    Interface.consolePrintln("Incorrect parametrs");
                    return;
                }

                GraphicsMain.clipping(new MyPoint(arr.get(0),arr.get(1)),new MyPoint(arr.get(2),arr.get(3)),new MyPoint(arr.get(4),arr.get(5)));
                return;
            }

            if(param.startsWith("copy")){
                param = param.substring("copy".length(),param.length());
                param = param.trim();

                String source = null;
                String copyName;

                if(param.indexOf(' ') != -1) {
                    source = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else{

                }

                param = param.trim();

                if(param.indexOf(' ') != -1) {
                    copyName = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    copyName = param.substring(0, param.length());
                GraphicsMain.copy(source,copyName);
            }

            if (param.startsWith("names")) {
                GraphicsMain.showNames();
                return;
            }

            if (param.startsWith("delete")) {

                param = param.substring("delete".length(),param.length());
                param = param.trim();
                String name;

                if(param.indexOf(' ') != -1) {
                    name = param.substring(0, param.indexOf(' '));
                }
                else
                    name = param.substring(0, param.length());

                GraphicsMain.delete(name);
                return;
            }

            if (param.startsWith("rotate")) {
                param = param.substring("rotate".length(),param.length());
                param = param.trim();

                String name;
                if(param.indexOf(' ') != -1) {
                    name = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else {
                    name = param.substring(0, param.length());
                    param = param.substring(param.length(),param.length());
                }

                param = param.trim();
                int znak = 1;
                if (param.startsWith("-")){
                    znak = -1;
                    param = param.substring(1,param.length());
                }
                 ArrayList<Integer> integers1 = new ArrayList<>();
                if (param.matches("\\d+\\d*")) {
                    Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
                    Matcher matcher = pattern.matcher(param);
                    while (matcher.find()) {
                        String s = matcher.group(0);
                        if (s.replaceAll("\\D", "").length() == s.length()) {
                            integers1 .add(Integer.parseInt(s));
                        }
                    }

                    if(integers1.size()<1){
                        Interface.consolePrintln("Incorrect parametrs");
                        return;
                    }

                    int alpha = integers1 .get(0);
                    GraphicsMain.rotate(name,znak * alpha);
                    return;
                }

                return;
            }

            if (param.startsWith("line")) {
                param = param.substring("line".length(),param.length());
                param = param.trim();
                String name;

                if(param.indexOf(' ') != -1) {
                    name = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    name = param.substring(0, param.length());

                Functional.currentName = name;
                Functional.drawLine();
            }

            if (param.startsWith("save")) {
                param = param.substring("save".length());
                param = param.trim();

                GraphicsMain.saveImage(param);
            }

            if (param.startsWith("load")) {
                param = param.substring("load".length());
                param = param.trim();

                GraphicsMain.loadImage(param);

                GraphicsMain.getApp().repaint();
            }

            if (param.equals("end")) {
                KeyBoardAdapter.enter = true;
                return;
            }

            if (param.startsWith("circle")) {

                param = param.substring("circle".length(),param.length());
                param = param.trim();
                String name;
                if(param.indexOf(' ') != -1) {
                    name = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    name = param.substring(0, param.length());

                Functional.currentName = name;
                Functional.drawCircle();
            }

            if (param.startsWith("bezier")) {
                param = param.substring("bezier".length(),param.length());
                param = param.trim();
                String name;
                if(param.indexOf(' ') != -1) {
                    name = param.substring(0, param.indexOf(' '));
                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                    name = param.substring(0, param.length());


                Functional.currentName = name;
                Functional.drawBezier();
            }

            if (param.matches("(( )*\\d+\\d*( )*)*")) {
                Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
                Matcher matcher = pattern.matcher(param);
                while (matcher.find()) {
                    String s = matcher.group(0);
                    if (s.replaceAll("\\D", "").length() == s.length()) {
                        integers.add(Integer.parseInt(s));
                    }
                }
            }


            if(param.startsWith("close")){
                param = param.substring("close".length(),param.length());
                param = param.trim();

                String name;

                if(param.indexOf(' ') != -1) {
                    name = param.substring(0, param.indexOf(' '));

                    param = param.substring(param.indexOf(' '),param.length());
                }
                else
                   return;

                param = param.trim();
                ArrayList<Integer> arr = new ArrayList<>();
                if (param.matches("\\d+\\d*( )*\\d+\\d*")) {
                    Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
                    Matcher matcher = pattern.matcher(param);
                    while (matcher.find()) {
                        String s = matcher.group(0);
                        if (s.replaceAll("\\D", "").length() == s.length()) {
                            arr.add(Integer.parseInt(s));
                        }
                    }

                    GraphicsMain.findClosest(name,new MyPoint(arr.get(0),arr.get(1)));
                    return;
                }


            }
        }catch(Exception e){
            Interface.consolePrintln("Invalid comand");
        }
    }

}
