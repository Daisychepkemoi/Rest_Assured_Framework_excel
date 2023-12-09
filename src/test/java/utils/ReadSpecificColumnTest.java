package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import io.restassured.RestAssured;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import static config.MainConfig.*;
import static utils.RestTemplate.*;


public class ReadSpecificColumnTest {


    //    @Test
//
//    public  void test_some_functions() throws IOException {
//        printColumn();
//        Assert.assertEquals(EXPECTED_STATUS_CODE, RESPONSE.getStatusCode());
//        System.out.println("**************************");
//        Assert.assertTrue(RESPONSE.asString().contains(EXPECTED_RESPONSE_BODY));
//
//    }
    @Test
    public  void printColumn() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(TEST_FILE_PATH));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);
        int rowCount = sheet.getLastRowNum();
        int columns = sheet.getDefaultColumnWidth();
        System.out.println("ROWCOUNT " + rowCount);
        System.out.println("Columns " + columns);
        for (int i = 0; i < columns-1; i++) {
            Cell cell = headerRow.getCell(i);
            String header_value = cell.getStringCellValue();
            check_And_Assign_Column_Index(header_value,i);
        }

        if(isColumnDefined()){
            assignColumns(rowCount,sheet);
            send_Request(METHOD);
        }

        else{
            throw new IllegalArgumentException("CHECK TO ENSURE KEY COLUMNS ARE DEFINED ON THE EXCEL SHEET. THE EXCEL MUST HAVE COLUMNS WITH NAME: Method_Type, Expected_Status_Code,Expected_Request_Body,Request_Body,Base_URL,Route,Headers");
        }

        workbook.close();
        inputStream.close();
    }

    static void check_And_Assign_Column_Index(String ColumnHeader, int columnIndex){

        if(ColumnHeader !=null) {
            switch (ColumnHeader) {
                case Method_Type:
                    Method_Type_Index = columnIndex;
                    break;
                case Expected_Status_Code:
                    Expected_Status_Code_Index = columnIndex;
                    break;
                case Expected_Request_Body:
                    Expected_Request_Body_Index = columnIndex;
                    break;
                case Request_Body:
                    Request_Body_Index = columnIndex;
                    break;
                case Base_URL:
                    Base_URL_Index = columnIndex;
                    break;
                case Route:
                    Route_Index = columnIndex;
                    break;
                case Headers:
                    Headers_Index = columnIndex;
                    break;
                default: {
                    System.out.println("The column header " + ColumnHeader + " is not defined. Check the config package to configure it");

                }


            }
        }
        else {
            System.out.println("the cell number  " + columnIndex + " is empty");
        }
    }

    static boolean isColumnDefined() {
        return Method_Type_Index != -1 && Expected_Status_Code_Index != -1 &&
                Expected_Request_Body_Index != -1 && Request_Body_Index != -1 &&
                Base_URL_Index != -1 && Route_Index != -1;
    }


    static void assignColumns(int rowCount, Sheet sheet){
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);

            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null && cell.getCellType() != CellType.BLANK) {

                BASE_URL =row.getCell(Base_URL_Index).getStringCellValue();

                METHOD =row.getCell(Method_Type_Index).getStringCellValue();

                REQUEST_BODY=row.getCell(Request_Body_Index).getStringCellValue();


                EXPECTED_STATUS_CODE= (int) row.getCell(Expected_Status_Code_Index).getNumericCellValue();

                EXPECTED_RESPONSE_BODY=row.getCell(Expected_Request_Body_Index).getStringCellValue();

                ROUTE=row.getCell(Route_Index).getStringCellValue();

                HEADERS=row.getCell(Headers_Index).getStringCellValue();





            }
        }
    }


    static  void send_Request(String method){
        RestAssured.baseURI = BASE_URL;
        System.out.println("*******METHOD********" + method);
        switch (method){
            case "POST" :
                POST_template(REQUEST_BODY,ROUTE);
                break;
            case "GET":
//                GET_Template_With_Param(REQUEST_BODY,ROUTE);
//                GET_Template_With_No_Params(ROUTE);
                GET_Template_With_No_Params_With_Access_Token(ROUTE,ACCESS_TOKEN);
                break;
            default: {
                System.out.println("The scenario " + method + " is not defined. Check the config package to configure it");

            }


        }

        Assert.assertEquals(EXPECTED_STATUS_CODE, RESPONSE.getStatusCode());
        System.out.println("**************************");
        Assert.assertTrue(RESPONSE.asString().contains(EXPECTED_RESPONSE_BODY));

    }
}
