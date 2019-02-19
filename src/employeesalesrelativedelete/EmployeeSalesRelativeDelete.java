
package employeesalesrelativedelete;

import java.io.*;
import java.nio.*;

public class EmployeeSalesRelativeDelete 
{

    
    public static void main(String[] args) 
    {
        //Files to read in
        String inFile = "E:/CIS3306 - Business Programming II/EmployeeSalesRelativeDelete/EmployeeSalesRelative.dat";
        
        
        //Length of record (8 for bytes)
        int recordLength = 8;
        //Using this to see if it is a prime number
        int primeNumber = 2113;
        int employeeNumber = 139654060; //Employee Number
        
        
        ByteBuffer aByteBuffer;
        
       
        
        try(RelativeFile outDataStream = new RelativeFile(inFile, primeNumber, recordLength);)
        {
            aByteBuffer = outDataStream.retreive(employeeNumber);
            
            System.out.println(aByteBuffer.getInt());
            System.out.println(aByteBuffer.getInt());
            outDataStream.deleteRecord(employeeNumber);
            
            aByteBuffer = outDataStream.retreive(employeeNumber);
            
            
            
            System.out.println("File Complete");
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        
        }
        
    }
    
}
