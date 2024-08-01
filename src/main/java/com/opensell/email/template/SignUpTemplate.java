package com.opensell.email.template;

public interface SignUpTemplate {
    static String getEmailVerification(String username, String code) {
        return HTML.formatted(username, code);
    }

    String HTML = """
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <html dir="ltr" lang="en">
        
          <head>
            <meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
            <meta name="x-apple-disable-message-reformatting" />
          </head>
        
          <body style="background-color:rgb(255,255,255);font-family:ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;">
            <table align="center" width="100%%" border="0" cellPadding="0" cellSpacing="0" role="presentation" style="max-width:37.5em">
              <tbody>
                <tr style="width:100%%">
                  <td><img src="https://raw.githubusercontent.com/HMDOC/readme-src/main/logo-title.png" style="display:block;outline:none;border:none;text-decoration:none" width="170px" />
                    <p style="font-size:14px;line-height:24px;margin:16px 0;color:rgb(0,0,0)">Hi %s! Thank you for signing up with Opensell.</p>
                    <p style="font-size:14px;line-height:24px;margin:16px 0;color:rgb(0,0,0)">Here&#x27;s your verification code :</p>
                    <p style="font-size:14px;line-height:24px;margin:16px 0;background-color:rgb(212,212,212);border-radius:0.25rem;padding:0.75rem;color:rgb(0,0,0)">%s</p>
                    <p style="font-size:14px;line-height:24px;margin:16px 0;color:rgb(0,0,0)">— The Opensell team</p>
                  </td>
                </tr>
              </tbody>
            </table>
          </body>
        
        </html>
        """;
}
