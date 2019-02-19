
package employeesalesrelativedelete;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class RelativeFile extends RandomAccessFile
{
    int primeNumber; //Primary number
    int recordLength; //we already know that the length has 8 bytes.
    
    RelativeFile(String ioFile, int primeNumberIn, int recordLengthIn) throws FileNotFoundException
    {
        super(ioFile, "rw");
        primeNumber = primeNumberIn;
        recordLength = recordLengthIn;
    
    }
    
   public int calculateRelativeKey(int i)
   {
       return (i % primeNumber) + 1;
   }
   
   public String checkRecordValid(int employeeNumber, int relativeKey) throws IOException
   {
       this.seek((relativeKey - 1) * recordLength);
       int storedEmployeeNumber = this.readInt();
       if(storedEmployeeNumber == 0)
       {
           return "No";
       }
       else
       {
           if(storedEmployeeNumber == employeeNumber)
           {
               return "Yes";
                       
           }
           else
           {
               System.out.print("Employee Number = " + employeeNumber + " ");
               System.out.println("Relative Key = " + relativeKey);
               
               return "Collision";
           }
       }
   }
   
   public ByteBuffer retreive(int employeeNumber) throws IOException
   {
       ByteBuffer aByteBuffer = null;
       
       byte [] aByteArray = new byte[recordLength];
       
       int relativeKey = this.calculateRelativeKey(employeeNumber);
       
       String validRecord = this.checkRecordValid(employeeNumber, relativeKey);
       
       switch(validRecord)
       {
           case "Yes":
           {
               this.seek((relativeKey - 1) * recordLength);
               this.readFully(aByteArray);
               aByteBuffer = ByteBuffer.wrap(aByteArray);
           
               break;
           }
           
           case "No":
           {
               System.out.println("Not a valid record");
               break;
           }
           case "Collision":
           {
               System.out.println("Error a collision");
               break;
           }

       
       }
       
       return aByteBuffer;
   }
   
   
   public void modifyField(int employeeNumber, int offset, int modifyValue) throws IOException
   {
       int relativeKey = calculateRelativeKey(employeeNumber);
       String validRecord = this.checkRecordValid(employeeNumber, relativeKey);
       
       switch(validRecord)
       {
           case "Yes":
           {
               this.seek((relativeKey - 1) * 8 + offset);
               this.writeInt(modifyValue);
               break;
           }
           case "No":
           {
               System.out.println("Not a valid Record");
               break;
           }
           case "Collision":
           {
               System.out.println("Error a collision");
               break;
           
           }
       
       
       }
   
   }
   
   public void addRecord(Employee employee) throws IOException
   {
       int employeeNumber = employee.getEmployeeNumber();
       int relativeKey = calculateRelativeKey(employeeNumber);
       String validRecord = this.checkRecordValid(employeeNumber, relativeKey);
       
       switch(validRecord)
       {
           case "Yes":
           {
               System.out.println("Error A Record Exists With This Employee #");
               break;
           }
           case "No":
           {
               this.seek((relativeKey - 1) * recordLength);
               this.write(employee.toBytes());
               break;
           }
           case "Collision":
           {
               System.out.println("Error a COLLISION!");
               break;
           }
       
       
       
       }
   }
   
   public void deleteRecord(int employeeNumber) throws IOException
   {
       int relativeKey = calculateRelativeKey(employeeNumber);
       String validRecord = this.checkRecordValid(employeeNumber, relativeKey);
       
       switch(validRecord)
       {
           case "Yes":
           {
               this.seek((relativeKey - 1) * recordLength);
               this.writeInt(0);//this.writeInt(0) zeros out bytes;
               break;
           }
           case "No":
           {
               System.out.println("Error A Record Does Not Exist With This Employee Number");
               break;
           }
           case "Collision":
           {
               System.out.println("Error a COLLISION!");
               break;
           }
       }
   }
   //logical delete - a field is that indicates the record has been deleted, while the
   //rest of the record is not deleted. 
   
   //physical delete - deletes the entire record. 
}
