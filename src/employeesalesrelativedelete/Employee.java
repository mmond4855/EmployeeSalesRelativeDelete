
package employeesalesrelativedelete;

import java.nio.*;

public class Employee 
{
    private int employeeNumber;
    private int sales;
    private int numberOfBytes = 8;
    
    Employee()
    {
    
    }
    
    Employee(int employeeNumberIn, int salesIn)
    {
        employeeNumber = employeeNumberIn;
        sales = salesIn;
    
    }
    
    public int getEmployeeNumber()
    {
        return this.employeeNumber;
    }
    
    public void setEmployeeNumber(int employeeNumber)
    {
        this.employeeNumber = employeeNumber;
    }
    
    public int getSales()
    {
        return this.sales;
    }
    
    public void setSales(int sales)
    {
        this.sales = sales;
    }
    
    public byte [] toBytes()
    {
        ByteBuffer aByteBuffer = ByteBuffer.allocate(numberOfBytes);
        aByteBuffer.putInt(employeeNumber);
        aByteBuffer.putInt(sales);
        
        byte [] aByteArray = aByteBuffer.array();
        return aByteArray;
    }
}
