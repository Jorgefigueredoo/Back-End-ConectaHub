package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.dto.RelatorioFiltroDTO;
import com.conectahub.conectahub_api.model.Envio;
import com.conectahub.conectahub_api.repository.EnvioRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private EnvioRepository envioRepository;

    public byte[] gerarRelatorioPdf(RelatorioFiltroDTO filtro) {
        // --- LOG DE DEPURAÇÃO ---
        System.out.println("--- GERANDO RELATÓRIO ---");
        System.out.println("Filtros recebidos:");
        System.out.println("Municipio: " + filtro.getMunicipio());
        System.out.println("Data Inicio: " + filtro.getDataInicio());
        System.out.println("Data Fim: " + filtro.getDataFim());

        // 1. Busca os dados no banco
        List<Envio> envios = envioRepository.filtrarEnvios(
            filtro.getAgricultorId(),
            filtro.getSementeId(),
            filtro.getMunicipio(),
            filtro.getDataInicio(),
            filtro.getDataFim()
        );

        System.out.println("Total de envios encontrados: " + envios.size());
        // -------------------------

        // 2. Cria o documento PDF
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
            Paragraph titulo = new Paragraph("Relatório de Envios - ConectaHub", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Tabela com 6 colunas
            PdfPTable table = new PdfPTable(6); 
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 2.5f, 2.0f, 2.0f, 1.2f, 1.8f}); 

            // Cabeçalho
            adicionarCabecalho(table, "Lote");
            adicionarCabecalho(table, "Agricultor");
            adicionarCabecalho(table, "Semente");
            adicionarCabecalho(table, "Município");
            adicionarCabecalho(table, "Qtd (Kg)");
            adicionarCabecalho(table, "Data");

            // Formatador de data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Envio envio : envios) {
                table.addCell(criarCelula(envio.getCodigoLote()));
                
                String nomeAgricultor = envio.getAgricultor() != null ? envio.getAgricultor().getNome() : "N/A";
                String municipio = envio.getAgricultor() != null ? envio.getAgricultor().getMunicipio() : "N/A";
                String semente = envio.getSemente() != null ? envio.getSemente().getTipoSemente() : "N/A";
                
                table.addCell(criarCelula(nomeAgricultor));
                table.addCell(criarCelula(semente));
                table.addCell(criarCelula(municipio));
                table.addCell(criarCelula(String.valueOf(envio.getQuantidadeEnviadaKg())));
                
                // DATA CORRIGIDA
                String dataFormatada = envio.getDataCriacao() != null ? envio.getDataCriacao().format(formatter) : "--";
                table.addCell(criarCelula(dataFormatada));
            }

            document.add(table);
            
            Paragraph rodape = new Paragraph("Total de registros: " + envios.size());
            rodape.setSpacingBefore(10);
            document.add(rodape);

            document.close();
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    private void adicionarCabecalho(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE)));
        cell.setBackgroundColor(new Color(26, 58, 104)); 
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private PdfPCell criarCelula(String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA, 10)));
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
}