package com.eazybooks.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Controller
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    /**
     * This method routes errors to the appropriate error page, defaulting to the plain error page if none found
     *
     * @param request HttpServletRequest to process
     * @return error page to be displayed
     */
    @GetMapping()
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int httpStatusCode = Integer.parseInt(status.toString());
            if (httpStatusCode == HttpStatus.NOT_FOUND.value()) {
                return "ErrorPages/error-404";
            } else if (httpStatusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "ErrorPages/error-505";
            }
        }
        return "error-page";
    }


    public String getErrorPath() {
        return null;
    }
}