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
 * @author espeer
 * <p>
 * (我们使用了@interface声明了Test注解，
 * 并使用@Target注解传入ElementType.METHOD参数来标明@Test只能用于方法上，
 * @Retention(RetentionPolicy.RUNTIME)则用来表示该注解生存期是运行时， 从代码上看注解的定义很像接口的定义，确实如此，毕竟在编译后也会生成Test.class文件。
 * 对于@Target和@Retention是由Java提供的元注解，所谓元注解就是标记其他注解的注解
 **/

/**
 *(我们使用了@interface声明了Test注解，
 *并使用@Target注解传入ElementType.METHOD参数来标明@Test只能用于方法上，
 *@Retention(RetentionPolicy.RUNTIME)则用来表示该注解生存期是运行时，
 *从代码上看注解的定义很像接口的定义，确实如此，毕竟在编译后也会生成Test.class文件。
 *对于@Target和@Retention是由Java提供的元注解，所谓元注解就是标记其他注解的注解
 **/

/**
 * Capability information位: 发送Beacon信号的时候，它被用来通知各方，该网络具备哪种性能
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Capability {
    String value();
}
