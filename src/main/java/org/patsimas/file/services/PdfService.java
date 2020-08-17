package org.patsimas.file.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface PdfService {

    void exportToPdf(HttpServletResponse response);
}
