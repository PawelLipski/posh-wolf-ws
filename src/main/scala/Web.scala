
/*
import org.jboss.netty.handler.codec.http.{HttpRequest, HttpResponse}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Response}
import com.twitter.finagle.Service
import com.twitter.util.Future
import java.net.InetSocketAddress
import util.Properties

object Web {
  def main(args: Array[String]) {
    val port = Properties.envOrElse("PORT", "8080").toInt
    println("Starting on port:"+port)
    ServerBuilder()
      .codec(Http())
      .name("hello-server")
      .bindTo(new InetSocketAddress(port))
      .build(new Hello)
    println("Started.")
  }
}

class Hello extends Service[HttpRequest, HttpResponse] {
  def apply(req: HttpRequest): Future[HttpResponse] = {
    val response = Response()
    response.setStatusCode(200)
    response.setContentString("Hello World")
    Future(response)
  }
} */

import java.net.InetSocketAddress
import javax.jws.WebService
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebParam.Mode
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.Use
import javax.jws.soap.SOAPBinding.Style
import javax.xml.ws.Endpoint
import util.Properties
 
@WebService (targetNamespace="org.scalabound.test") //, name="org.scalabound.test", portName="test", serviceName="WSTest")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL) 
private class MinimalSoapServer {
 
    @WebMethod
    def test(
    @WebParam(targetNamespace="org.scalabound.test", name="value", mode=Mode.IN)
    value : String) = "Hi " + value
 
}
object Web {               
    def main(args: Array[String]) { // main method to make this a runnable application
        val port = Properties.envOrElse("PORT", "8080")
	val url = "http://localhost:" + port + "/wstest";
        val endpoint = Endpoint.publish(url, new MinimalSoapServer())
	println(url)

	val url2 = (new InetSocketAddress(port.toInt)).toString + "/wstest"
	println("Alternative URL: " + url2)
    	//val port = Properties.envOrElse("PORT", "8080").toInt
	//Endpoint.publish(url, new MinimalSoapServer())

        System.out.println("Waiting for requests...")
    }
}

