
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

import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.Style
import java.net.InetSocketAddress
import javax.xml.ws.Endpoint
import util.Properties
 
@WebService(targetNamespace="org.scalabound.test", name="org.scalabound.test", portName="test", serviceName="WSTest")
private class MinimalSoapServer {
 
    @SOAPBinding(style = Style.RPC)
    def test(value : String) = "Hi " + value
 
}
object Web {               
    def main(args: Array[String]) { // main method to make this a runnable application
        //val port = Properties.envOrElse("PORT", "8080")
        //val endpoint = Endpoint.publish("http://localhost:" + port + "/wstest", new MinimalSoapServer())
    	val port = Properties.envOrElse("PORT", "8080").toInt
	val url = (new InetSocketAddress(port)).toString + "/wstest"
	println(url)
	Endpoint.publish(url, new MinimalSoapServer())
        System.out.println("Waiting for requests...")
    }
}

