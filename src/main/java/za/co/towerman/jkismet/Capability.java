/**
 * za.co.towerman.jkismet.Capability
 * Copyright (C) 2012 Edwin Peer
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package za.co.towerman.jkismet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author espeer
 **/


/**
 * Capability注解，用于表示各类参数
 */


@Target(ElementType.METHOD)             // 使用@Target注解传入ElementType.METHOD参数来标明该注解只能用于方法上
@Retention(RetentionPolicy.RUNTIME)     // 使用@Retention(RetentionPolicy.RUNTIME)用来表示该注解生存期是运行时
public @interface Capability {
    String value();
}
