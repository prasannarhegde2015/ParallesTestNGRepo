package com.nordea.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtils {

	@SuppressWarnings("deprecation")
	public List<Map<String, String>> getResultSetPOI(String file, String sheet, String colname, String colvalue)
			throws Exception {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		// WritetoLog("Inside getResultSetPOI:");
		FileInputStream filexls = null;
		HSSFWorkbook workbook = null;
		try {
			// WritetoLog("Inside Try ");
			// WritetoLog("File path: " + file);
			// // ..
			filexls = new FileInputStream(new File(file));
			// Get the workbook instance for XLS file
			workbook = new HSSFWorkbook(filexls);
			// Get first sheet from the workbook
			HSSFSheet retsheet = workbook.getSheet(sheet);
			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = retsheet.iterator();
			Integer rownum = 0;
			if (colname.length() > 0 && colvalue.length() > 0) {
				// WritetoLog("Inside Filter Case for Colname" + colname
				// + " and clovalue " + colvalue);
				String columnName = "";
				Map<String, String> row = null;
				ArrayList<String> colnamelist = new ArrayList<String>();
				while (rowIterator.hasNext()) {
					WritetoLog("Row number: " + rownum);
					Row indrow = rowIterator.next();
					row = new HashMap<String, String>();
					// For each row, iterate through each columns
					Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = indrow.cellIterator();
					Integer colnum = 0;
					while (cellIterator.hasNext()) {
						org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
						// row = new HashMap<String, String>();
						if (((org.apache.poi.ss.usermodel.Cell) cell)
								.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
							String value = cell.getStringCellValue();
							// WritetoLog("got cell value as " + value);
							if (rownum == 0) {
								columnName = value;
								colnamelist.add(colnum, columnName);
							} else {
								row.put(colnamelist.get(colnum), value);
							}
						}
						colnum++;
					}
					// WritetoLog("Checking if Row to be added or not ");
					// WritetoLog("Column name " + colname + " column value "
					// + colvalue);
					// if (row.containsKey(colname)
					// && row.get(colname) == colvalue) {
					// WritetoLog("Value from Dictionany or hashmap was"
					// + row.get(colname));
					if (row.get(colname) != null) {
						if (row.get(colname).equalsIgnoreCase(colvalue)) {
							// WritetoLog("Adding ..row with Column name "
							// + colname + " column value " + colvalue);
							resultList.add(row);
						} else {
							/*
							 * WritetoLog("Nothing was added as row containes "
							 * + colname + " is " + row.containsKey(colname) +
							 * "and row cotnain colval " + colvalue + " is " +
							 * (row.get(colname) == colvalue));
							 */
						}
					}
					rownum++;
				}
			} else {
				// WritetoLog("Inside Case for Full record set records
				// unfiltered ");
				String columnName = "";
				ArrayList<String> colnamelist = new ArrayList<String>();
				Map<String, String> row = null;
				while (rowIterator.hasNext()) {
					// WritetoLog("Row number: " + rownum);
					Row indrow = rowIterator.next();
					row = new HashMap<String, String>();
					// For each row, iterate through each columns
					Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = indrow.cellIterator();
					Integer colnum = 0;
					while (cellIterator.hasNext()) {
						WritetoLog("Column number: " + colnum);
						org.apache.poi.ss.usermodel.Cell cell = (org.apache.poi.ss.usermodel.Cell) cellIterator.next();
						// row = new HashMap<String, String>();
						if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
							String value = cell.getStringCellValue();
							// WritetoLog("got cell value as " + value);
							if (rownum == 0) {
								columnName = value;
								colnamelist.add(colnum, columnName);
								row.put(colnamelist.get(colnum), value);
							} else {
								row.put(colnamelist.get(colnum), value);
							}
						}
						colnum++;
					}
					resultList.add(row);
					rownum++;
				}
			}
			// Get iterator to all cells of current row
			// WritetoLog("outsude record set");
			workbook.close();
			WritetoLog("returned record set in form of List.. ");
			return resultList;
		} catch (Exception e) {
			System.err.println(e);
			return resultList;
		} finally {
			try {
				filexls.close();
				// workbook.close();
				return resultList;
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public void WritetoLog(String FileNametoWriote, String strlogmsg) throws IOException {
		File resultcsvfile = new File(FileNametoWriote);
		if (!resultcsvfile.exists()) {
			try {
				System.out.println("File is not there Creating File");
				resultcsvfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter writer = null;
		writer = new FileWriter(resultcsvfile, true);
		BufferedWriter bwriter = new BufferedWriter(writer);
		bwriter.append(strlogmsg);
		bwriter.newLine();
		bwriter.close();
	}

	public void WritetoLog(String strlogmsg) throws IOException {
		File resultcsvfile = new File("c:\\temp\\results.txt");
		if (!resultcsvfile.exists()) {
			try {
				System.out.println("File is not there Creating File");
				resultcsvfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter writer = null;
		writer = new FileWriter(resultcsvfile, true);
		BufferedWriter bwriter = new BufferedWriter(writer);
		bwriter.append(strlogmsg);
		bwriter.newLine();
		bwriter.close();
	}

	public static int getIndexonName(ResultSet r1, String colName) {
		int Colindex = 0;
		try {
			Colindex = r1.findColumn(colName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Colindex;
	}

	// For Java 1.7
	public List<Map<String, String>> getResultSet(String file, String sheet, String colname, String colvalue)
			throws Exception {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		WritetoLog("Inside Add Data");
		ResultSet rs = null;
		Connection c = null;
		String query = "";
		java.sql.Statement stmnt = null;
		try {
			WritetoLog("Inside Try ");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			WritetoLog("found class ");
			WritetoLog("file path " + file);
			c = DriverManager.getConnection(
					"jdbc:odbc:DRIVER={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ=" + file + ";");
			WritetoLog("file path " + file);
			stmnt = c.createStatement();
			if (colname.length() > 0 && colvalue.length() > 0) {
				query = "select * from [" + sheet + "$] where " + colname + " ='" + colvalue + "'";
				WritetoLog("query " + query);
			} else {
				query = "select * from [" + sheet + "$]";
				WritetoLog("query " + query);
			}
			rs = stmnt.executeQuery(query);
			Map<String, String> row = null;
			ResultSetMetaData metaData = rs.getMetaData();
			Integer columnCount = metaData.getColumnCount();
			WritetoLog("outsude record set");
			while (rs.next()) {
				row = new HashMap<String, String>();
				for (int i = 1; i <= columnCount; i++) {
					WritetoLog("Inside REcord Set loop");
					String strcolname = metaData.getColumnName(i).toString();
					String strcolvalue = rs.getString(i);
					WritetoLog(" Column name is " + strcolname);
					WritetoLog(" Value of Column " + i + " == " + strcolvalue);
					if (strcolvalue == null) {
						strcolvalue = "";
					}
					row.put(strcolname, strcolvalue);
					WritetoLog("Inserted value" + strcolvalue + "for column is" + strcolname);
				}
				resultList.add(row);
			}
			WritetoLog("retuned record set in form of List ");
			return resultList;
		} catch (Exception e) {
			System.err.println(e);
			return resultList;
		} finally {
			try {
				stmnt.close();
				c.close();
				return resultList;
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}
