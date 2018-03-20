import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.java.browser.plugin2.DOM;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlResolve {
    /**
     * DOM方式解析xml
     */
    static class DOMResolve {
        public static void main(String[] args) {
            //1、创建一个DocumentBuilderFactory的对象
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //2、创建一个DocumentBuilder的对象
            try {
                //创建DocumentBuilder对象
                DocumentBuilder db = dbf.newDocumentBuilder();
                //3、通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
                /*注意导入Document对象时，要导入org.w3c.dom.Document包下的*/
                Document document = db.parse("amazing-rest\\src\\main\\resources\\book.xml");//传入文件名可以是相对路径也可以是绝对路径
                new DOMResolve().findXml(document);
                //获取所有book节点的集合
//                NodeList bookList = document.getElementsByTagName("book");
//                //通过nodelist的getLength()方法可以获取bookList的长度
//                System.out.println("一共有" + bookList.getLength() + "本书");
//                //遍历每一个book节点
//                for (int i = 0; i < bookList.getLength(); i++) {
//                    System.out.println("=================下面开始遍历第" + (i + 1) + "本书的内容=================");
//                    //❤未知节点属性的个数和属性名时:
//                    //通过 item(i)方法 获取一个book节点，nodelist的索引值从0开始
//                    Node book = bookList.item(i);
//                    //获取book节点的所有属性集合
//                    NamedNodeMap attrs = book.getAttributes();
//                    System.out.println("第 " + (i + 1) + "本书共有" + attrs.getLength() + "个属性");
//                    //遍历book的属性
//                    for (int j = 0; j < attrs.getLength(); j++) {
//                        //通过item(index)方法获取book节点的某一个属性
//                        Node attr = attrs.item(j);
//                        //获取属性名
//                        System.out.print("属性名：" + attr.getNodeName());
//                        //获取属性值
//                        System.out.println("--属性值" + attr.getNodeValue());
//                    }
//
//                    //解析book节点的子节点
//                    NodeList childNodes = book.getChildNodes();
//                    //遍历childNodes获取每个节点的节点名和节点值
//                    System.out.println("第" + (i+1) + "本书共有" + childNodes.getLength() + "个子节点");
//                    for (int k = 0; k < childNodes.getLength(); k++) {
//                        //区分出text类型的node以及element类型的node
//                        if(childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
//                            //获取了element类型节点的节点名
//                            System.out.print("第" + (k + 1) + "个节点的节点名：" + childNodes.item(k).getNodeName());
//                            //获取了element类型节点的节点值
//                            System.out.println("--节点值是：" + childNodes.item(k).getFirstChild().getNodeValue());
////                        System.out.println("--节点值是：" + childNodes.item(k).getTextContent());
//                        }
//                    }
//                    System.out.println("======================结束遍历第" + (i + 1) + "本书的内容=================");
//                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void findXml(Document document) {
            NodeList nodeList = document.getElementsByTagName("book");
            System.out.println(nodeList.getLength());
            for(int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                NamedNodeMap namedNodeMap = node.getAttributes();
                for(int j=0; j<namedNodeMap.getLength(); j++) {
                    System.out.print("##" + namedNodeMap.item(j).getNodeValue());
                    System.out.print(namedNodeMap.item(j).getNodeName());
                    System.out.println("--" + namedNodeMap.item(j).getNodeValue());
                }
                NodeList nodeListchild = node.getChildNodes();
                for(int k=0; k<nodeListchild.getLength(); k++) {
                    System.out.println("##" + nodeListchild.item(k).getNodeType());
                    if(nodeListchild.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        System.out.print( nodeListchild.item(k).getNodeName());
                        System.out.println("--" + nodeListchild.item(k).getFirstChild().getNodeValue());
                    }
                }
            }
        }
    }
}
