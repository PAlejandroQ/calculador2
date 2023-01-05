package com.alejandroqo.calculador;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
 
@Service 
public class Calculador { 
     @Cacheable("sum")
     public int sum(int a, int b) {
//          try {
//               Thread.sleep(3000);
//          }
//          catch (InterruptedException e) {
//               e.printStackTrace();
//          }
          return a + b;
     } 
} 
