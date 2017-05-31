//import java.util.ArrayList;
//import java.util.HashMap;
//
//// Given a useful class from a Java library...
//
////Hi Mark, Satyen , Here
//
///**
// * Parses lines from a CSV file, each representing 1 row,
// * into individual columns.
// */
//class CsvParser implements Iterator<String[]> {
//  /** True if there are more lines. */
//  public boolean hasNext();
//
//  /** Return the next line, parsed into columns, each as a string. */
//  public String[] next();
//}
//
//
//// ... write a method that does the following:
//
///**
// * Tally up the sales results from the quarter.
// * 
// * <p>This method consumes a CSV file describing the quarterly sales report,
// * and returns aggregate statistics about the input data.</p>
// * 
// * <p>The sales report input follows a CSV format with columns like the
// * following:</p>
// * <div><code><pre>
// * Week    Product1  Product2  Product3 ...
// *    0      568.15    180.12    513.40
// *    1      581.34    312.01    480.02
// *   ...
// *   11      545.34    134.62    502.10
// * </pre></code></div>
// *
// * <p>For each week, we display the sales generated from each of N products
// * represented in the second through N+1’th columns of the CSV. The 1st
// * column indicates the week number.</p>
// *
// * <p>Each quarter consists of 12 weeks. This method generates
// * a sales report with the following aggregate data:</p>
// *
// * <ul>
// *   <li>The total value associated with each week.</li>
// *   <li>Identify the week with the highest sales.</li>
// * </ul>
// * 
// * @param parser a parser for the input CSV file.
// * @return a SalesReport containing the figures of merit described above.
// */
//public SalesReport generateSalesReport(CsvParser parser) {
//  SalesReport report = new SalesReport();
//  // TODO: Write this method body.
//    String[] headers = parser.next();
//  int headCnt = 1;
//  
//  for(int i=1; i< headers.length; i++){
//    report.products.add(headers[i]);
//  }
//
//  while(parser.hasNext()){
//    Float tmpTotal = 0.0;
//    Float highestSale = 0.0;
//    int hgSaleIndex = -1;
//    String[] columsn = parser.next();
//    //ArrayList<Float> sales = new ArrayList<Float>();
//    
//    for(int i=1; i<columns.length; i++){
//      String prod = report.products.get(i-1);
//      if(report.map.contains(prod)){
//        report.map.get(prod).add(Float.valueOf(columns[i]));
//      }else{
//        ArrayList<Float> list = new ArrayList<Float>();
//        list.add(Float.valueOf(columns[i]));
//        report.map.put(prod, list);
//      }
//      tmpTotal += Float.valueOf(columns[i]);
//    }
//    if(tmpTotal > highestSale){
//      highestSale = tmpTotal;
//      hgSaleIndex = headCnt;
//    }
//    report.highSaleWeek = hgSaleIndex;
//    report.totalValue.add(tmpTotal);
//    map.put(headers[headCnt], sales);
//    headCnt++;
//  }
//
//  return report;
//}
//
//public class SalesReport {
//  public static HashMap<String, ArrayList<Float>> map = new HashMap<String, ArrayList<Float>>();
//   public static ArrayList<String> products = new ArrayList<String>();  //TODO check
//  public static ArrayList<Float> totalValue = new ArrayList<Float>();  //TODO check
//public static Integer highSaleWeek = -1;  //TODO check
//  // TODO: Write this class body.
//  // TODO: Don’t worry about getters or setters. public fields are fine.
//
//}
//
//
//// Part 2: Assume the following additional requirements are added to the SalesReport returned by generateSalesReport():
//
///**
// * <p>Your SalesReport must also contain:</p>
// * <ul>
// *   <li>The total sales associated with each product over the quarter.</li>
// *   <li>The average weekly sales associated with each product.</li>
// *   <li>Products should be indexed by product name in the report.</li>
// * </ul>
// */   
