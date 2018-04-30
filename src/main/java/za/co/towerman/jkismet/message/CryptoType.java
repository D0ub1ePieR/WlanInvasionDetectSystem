/**
 * za.co.towerman.jkismet.message.CryptoType
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
package za.co.towerman.jkismet.message;

/**
 *
 * @author espeer
 */

/*
 * 加密类型
 */

public enum CryptoType {
    NONE,
    UNKNOWN,
    WEP,
    LAYER3,
    WEP40,
    WEP104,
    TKIP,
    WPA,
    WPA_PSK,
    WPA_AES_OCB,
    WPA_AES_CCM,
    WPA_LEAP,
    WPA_TTLS,
    WPA_TLS,
    WPA_PEAP,
    ISAKMP,
    PPTP,
    FORTRESS,
    KEYGUARD,
    UNKNOWN_NONWEP,
    WPA_MIGRATION
}
