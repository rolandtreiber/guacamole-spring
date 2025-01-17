package com.guacamole.guacamole.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuacamoleTunnelServlet extends GuacamoleHTTPTunnelServlet {

        @Override
        protected GuacamoleTunnel doConnect(HttpServletRequest request)
        throws GuacamoleException {

            // Create our configuration
            GuacamoleConfiguration config = new GuacamoleConfiguration();
            config.setProtocol("rdp");
            config.setParameter("hostname", "localhost");
            config.setParameter("port", "3389");
//            config.setParameter("password", "potato");

            // Connect to guacd - everything is hard-coded here.
            GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                    new InetGuacamoleSocket("", 4822),
                    config
            );

            // Return a new tunnel which uses the connected socket
            return new SimpleGuacamoleTunnel(socket);

        }

    @RequestMapping(path = "tunnel", method = { RequestMethod.POST, RequestMethod.GET })
    protected void handleTunnelRequest(HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException {
        super.handleTunnelRequest(request, response);
    }
}
