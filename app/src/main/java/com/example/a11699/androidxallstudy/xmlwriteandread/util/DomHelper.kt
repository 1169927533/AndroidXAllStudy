package com.example.a11699.androidxallstudy.xmlwriteandread.util

import android.content.Context
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

/**
 * @Author Yu
 * @Date 2020/11/4 11:26
 * @Description TODO
 */
object DomHelper{
     fun queryXml(context: Context): ArrayList<Person> {
        var arrayList = ArrayList<Person>()
        //①获得DOM解析器的工厂示例:
        val dbFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        //②从Dom工厂中获得dom解析器
        val dbBuilder: DocumentBuilder = dbFactory.newDocumentBuilder()
        //③把要解析的xml文件读入Dom解析器
        val doc: Document = dbBuilder.parse(context.assets.open("person2.xml"))
        System.out.println("处理该文档的DomImplemention对象=" + doc.getImplementation())
        //④得到文档中名称为person的元素的结点列表
        val nList: NodeList = doc.getElementsByTagName("person")
        for (i in 0 until nList.length) {
            //先从Person元素开始解析
            var personElement = nList.item(i) as Element
            var p = Person()
            p.setId(Integer.valueOf(personElement.getAttribute("id")));

            //获取person下的name和age的Note集合
            var childNoList = personElement.childNodes
            for (j in 0 until childNoList.length) {
                var childNode = childNoList.item(j);
                //判断子note类型是否为元素Note
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    var childElement = childNode
                    if ("name".equals(childElement.getNodeName()))
                        p.setName(childElement.getFirstChild().getNodeValue());
                    else if ("age".equals(childElement.getNodeName()))
                        p.setAge(Integer.valueOf(childElement.getFirstChild().getNodeValue()));
                }
            }
            arrayList.add(p)
        }
        return arrayList
    }
}