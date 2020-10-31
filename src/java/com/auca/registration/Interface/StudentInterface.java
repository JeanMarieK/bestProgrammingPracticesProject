
package com.auca.registration.Interface;

import java.sql.Connection;
import com.auca.registration.domain.Student;

/**
 *
 * @author JK
 */
public interface StudentInterface {
    public Connection con();
    public void save(Student st);
}
