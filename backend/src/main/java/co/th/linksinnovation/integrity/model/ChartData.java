/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.model;

import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Jirawong
 */
@Data
public class ChartData {
    private String name;
    private BigDecimal done;
    private BigDecimal pass;
    private BigDecimal total;
}
