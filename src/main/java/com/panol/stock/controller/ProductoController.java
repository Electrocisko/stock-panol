package com.panol.stock.controller;

import com.panol.stock.dto.*;
import com.panol.stock.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // OBTENER STOCK BAJO
    @GetMapping("/stock-bajo")
    public List<ProductoStockBajoResponse> stockBajo() {
        return service.obtenerStockBajo();
    }

    // OBTENER STOCK Y DETALLE DE MOVIMIENTOS
    @GetMapping("/{id}/detalle")
    public ProductoDetalleResponse detalle(@PathVariable Long id) {
        return service.obtenerDetalle(id);
    }

    //OBTENER POR ID
    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    // CREAR
    @PostMapping
    public ProductoResponse crear(@RequestBody ProductoRequest request, HttpServletRequest httpRequest) {

        String rol = (String) httpRequest.getAttribute("rol");
        String username = (String) httpRequest.getAttribute("username");


        return service.crear(request, rol, username);
    }

    //LISTAR
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 esto aparece en logs de Railway
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id,
                                       @RequestBody ProductoRequest request,
                                       HttpServletRequest httpRequest) {
        String rol = (String) httpRequest.getAttribute("rol");
        return service.actualizar(id, request, rol);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id, HttpServletRequest httpRequest) {
        String rol = (String) httpRequest.getAttribute("rol");
        service.eliminar(id, rol);
    }

    //EXPORTAR
    @GetMapping("/exportar")
    public void exportarProductos(HttpServletResponse response) throws IOException {

        try {

            List<ProductoExportResponse> productos = service.listarParaExportar();
            System.out.println("Productos: " + productos.size());

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Stock");

            int rowNum = 0;

            // 🔹 HEADER
            Row header = sheet.createRow(rowNum++);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Código");
            header.createCell(2).setCellValue("Nombre");
            header.createCell(3).setCellValue("Categoría");
            header.createCell(4).setCellValue("Cantidad");
            header.createCell(5).setCellValue("Stock Mínimo");
            header.createCell(6).setCellValue("Ubicación");

            // 🔹 ESTILO HEADER (negrita)
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);

            for (Cell cell : header) {
                cell.setCellStyle(style);
            }

            // 🔹 DATOS
            for (ProductoExportResponse p : productos) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(p.getId() != null ? p.getId() : 0);
                row.createCell(1).setCellValue(p.getCodigo() != null ? p.getCodigo() : "");
                row.createCell(2).setCellValue(p.getNombre() != null ? p.getNombre() : "");
                row.createCell(3).setCellValue(p.getCategoria() != null ? p.getCategoria() : "");
                row.createCell(4).setCellValue(p.getCantidad());
                row.createCell(5).setCellValue(p.getStockMinimo() != null ? p.getStockMinimo() : 0);
                row.createCell(6).setCellValue(p.getUbicacion() != null ? p.getUbicacion() : "");
            }

            // 🔹 AJUSTAR COLUMNAS
            for (int i = 0; i <= 6; i++) {
                sheet.autoSizeColumn(i);
            }

            // 🔹 FREEZE HEADER
            sheet.createFreezePane(0, 1);

            // 🔹 RESPONSE
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 importante para Railway
            throw e;
        }
    }

}
