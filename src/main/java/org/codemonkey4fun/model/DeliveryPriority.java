/*
 * Copyright (c) 2013-2017 Turo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.codemonkey4fun.model;

/**
 * The priority of the notification
 * @see <a href=
 *      "https://developer.apple.com/library/content/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/CommunicatingwithAPNs.html">
 *      Local and Remote Notification Programming Guide - Communicating with APNs</a>
 */
public enum DeliveryPriority {
    /**
     * <p>Send the push message immediately.
     * Notifications with this priority must trigger an alert, sound, or badge on the target device.
     * It is an error to use this priority for a push notification that contains only the {@code content-available} key </p>
     */
    IMMEDIATE(10),
    /**
     * <p>Send the push message at a time that takes into account power considerations for the device.
     * Notifications with this priority might be grouped and delivered in bursts. They are throttled, and in some cases are not delivered.</p>
     */
    CONSERVE_POWER(5);

    private final int code;

    DeliveryPriority(final int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static DeliveryPriority convert(final int code) {
        for (final DeliveryPriority priority : DeliveryPriority.values()) {
            if (priority.code() == code) {
                return priority;
            }
        }

        throw new IllegalArgumentException(String.format("No delivery priority found with code %d", code));
    }
}
