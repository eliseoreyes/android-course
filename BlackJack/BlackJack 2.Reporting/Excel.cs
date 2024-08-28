using ClosedXML.Excel;
using DocumentFormat.OpenXml.Drawing;
using iText.Kernel.Pdf;
using iText.Layout;
using iText.Layout.Element;

namespace BlackJack.Reporting
{
    public static class Excel
    {
        public static void Export(string filename, string[,] data)
        {
            try
            {
                IXLWorkbook xLWorkbook = new XLWorkbook();
                IXLWorksheet xLWorksheet = xLWorkbook.AddWorksheet("Data");

                int rows = data.GetLength(0);
                int columns = data.GetLength(1);

                PdfWriter writer = new PdfWriter(filename.Substring(0, filename.Length - 4) + "pdf");
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                iText.Layout.Element.Paragraph header = new iText.Layout.Element.Paragraph("Data")
                    .SetTextAlignment(iText.Layout.Properties.TextAlignment.CENTER)
                    .SetFontSize(20);
                document.Add(header);

                iText.Layout.Element.Paragraph subheader = new iText.Layout.Element.Paragraph("Information")
                    .SetTextAlignment(iText.Layout.Properties.TextAlignment.CENTER)
                    .SetFontSize(15);
                document.Add(subheader);

                iText.Layout.Element.Table table = new iText.Layout.Element.Table(columns, false);

                for (int iRow = 1; iRow <= rows; iRow++)
                {
                    for (int iCol = 1; iCol <= columns; iCol++)
                    {
                        //	Excell cell
                        xLWorksheet.Cell(iRow, iCol).Value = data[iRow - 1, iCol - 1];

                        // PDF cell
                        Cell cell = new Cell(1, 1);
                        cell.Add(new iText.Layout.Element.Paragraph(data[iRow - 1, iCol - 1]));
                        table.AddCell(cell);

                    }
                }
                document.Add(table);
                document.Close();
                xLWorkbook.SaveAs(filename);
            }
            catch (Exception)
            {

                throw;
            }
        }
    }
}