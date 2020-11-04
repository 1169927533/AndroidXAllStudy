package com.example.a11699.androidxallstudy.xmlwriteandread

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a11699.androidxallstudy.R
import com.example.a11699.androidxallstudy.xmlwriteandread.util.DomHelper
import com.example.a11699.androidxallstudy.xmlwriteandread.util.Person
import com.example.a11699.androidxallstudy.xmlwriteandread.util.PullHelper
import com.example.a11699.androidxallstudy.xmlwriteandread.util.SaxHelper
import kotlinx.android.synthetic.main.activity_xml.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory


/**
 * @Author Yu
 * @Date 2020/11/4 11:08
 * @Description TODO
 */
class XmlStudyActivity:AppCompatActivity() {
    var persons = ArrayList<Person>()
    lateinit var mAdapter:ArrayAdapter<Person>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml)
        btnsax.setOnClickListener {
            persons =readXmlForSAX()
            mAdapter = ArrayAdapter<Person>(this, R.layout.support_simple_spinner_dropdown_item, persons)
            list.adapter = mAdapter
        }
        btndom.setOnClickListener {
            persons = DomHelper.queryXml(applicationContext)
            mAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, persons)
            list.adapter = mAdapter
        }
        btnpullread.setOnClickListener {
            //获取文件资源建立输入流对象
            try {
                val `is` = assets.open("person3.xml")
                persons = PullHelper.getPersons(`is`)
                if (persons == null) {
                    Toast.makeText(applicationContext, "呵呵", Toast.LENGTH_SHORT).show()
                }
                for (p1 in persons) {
                    Log.i("逗比", p1.toString())
                }
                mAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, persons)
                list.adapter = mAdapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        btnpullwrite.setOnClickListener {
            val context: Context = applicationContext
            val persons: MutableList<Person> = ArrayList()
            persons.add(Person(21, "逗比1", 70))
            persons.add(Person(31, "逗比2", 50))
            persons.add(Person(11, "逗比3", 30))
            val xmlFile = File(context.getFilesDir(), "jay.xml")
            val fos: FileOutputStream
            try {
                fos = FileOutputStream(xmlFile)
                PullHelper.save(persons, fos)
                Toast.makeText(context, "文件写入完毕", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun readXmlForSAX():ArrayList<Person>{
        //获取文件资源建立输入流对象
        val `is`: InputStream = assets.open("person1.xml")
        //①创建XML解析处理器
        val ss = SaxHelper()
        //②得到SAX解析工厂
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        //③创建SAX解析器
        val parser: SAXParser = factory.newSAXParser()
        //④将xml解析处理器分配给解析器,对文档进行解析,将事件发送给处理器
        parser.parse(`is`, ss)
        `is`.close()
        return ss.persons;
    }
}