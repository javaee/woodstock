/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

@JS_NS@._dojo.provide("@JS_NS@.json");

//
// Copyright (c) 2005 JSON.org
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The Software shall be used for Good, not Evil.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//

/**
 * @class This class contains functions for parsing JSON.
 * @static
 */
@JS_NS@.json = {
    /**
     * JSON escape chars.
     * @private
     */
    _m: {
        '\b': '\\b',
        '\t': '\\t',
        '\n': '\\n',
        '\f': '\\f',
        '\r': '\\r',
        '"' : '\\"',
        '\\': '\\\\'
    },

    /**
     * JSON parsor.
     * @private
     */
    _s: {
        /** @ignore */
        'boolean': function (x) {
            return String(x);
        },
        /** @ignore */
        number: function (x) {
            return isFinite(x) ? String(x) : null;
        },
        /** @ignore */
        string: function (x) {
            if (/["\\\x00-\x1f]/.test(x)) {
                x = x.replace(/([\x00-\x1f\\"])/g, function(a, b) {
                    var c = @JS_NS@.json._m[b];
                    if (c) {
                        return c;
                    }
                    c = b.charCodeAt();
                    return '\\u00' +
                        Math.floor(c / 16).toString(16) +
                        (c % 16).toString(16);
                });
            }
            return '"' + x + '"';
        },
        /** @ignore */
        object: function (x) {
            if (x) {
                var a = [], b, f, i, l, v;
                if (x instanceof Array) {
                    a[0] = '[';
                    l = x.length;
                    for (i = 0; i < l; i += 1) {
                        v = x[i];
                        f = @JS_NS@.json._s[typeof v];
                        if (f) {
                            v = f(v);
                            if (typeof v == 'string') {
                                if (b) {
                                    a[a.length] = ',';
                                }
                                a[a.length] = v;
                                b = true;
                            }
                        }
                    }
                    a[a.length] = ']';
                } else if (typeof x.hasOwnProperty === 'function') {
                    a[0] = '{';
                    for (i in x) {
                        if (x.hasOwnProperty(i)) {
                            v = x[i];
                            f = @JS_NS@.json._s[typeof v];
                            if (f) {
                                v = f(v);
                                if (typeof v == 'string') {
                                    if (b) {
                                        a[a.length] = ',';
                                    }
                                    a.push(@JS_NS@.json._s.string(i), ':', v);
                                    b = true;
                                }
                            }
                        }
                    }
                    a[a.length] = '}';
                } else {
                    return null;
                }
                return a.join('');
            }
            return null;
        }
    },

    /**
     * Stringify a JavaScript value, producing JSON text. 
     *
     * @param {Object} v A non-cyclical JSON object.
     * @return {boolean} true if successful; otherwise, false.
     */
    stringify: function (v) {
        var f = @JS_NS@.json._s[typeof v];
        if (f) {
            v = f(v);
            if (typeof v == 'string') {
                return v;
            }
        }
        return null;
    },

    /**
     * Parse a JSON text, producing a JavaScript object.
     *
     * @param {String} text The string containing JSON text.
     * @return {boolean} true if successful; otherwise, false.
     */
    parse: function (text) {
        try {
            return !(/[^,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]/.test(
                text.replace(/"(\\.|[^"\\])*"/g, ''))) && eval('(' + text + ')');
        } catch (e) {
            return false;
        }
    }
};
