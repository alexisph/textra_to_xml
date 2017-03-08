#!/usr/bin/env groovy

/*
 * Copyright (C) 2017 Alexander Phinikarides, alexisph@gmail.com
 * Inspired by: https://together.jolla.com/question/54249/how-to-saving-sms-text-conversations/?answer=102213#post-id-102213 by Sami Boukortt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

@Grapes([
    @GrabConfig(systemClassLoader=true),
    @Grab('org.xerial:sqlite-jdbc:3.8.11.2')
])

import groovy.sql.Sql
import groovy.xml.MarkupBuilder

// Change these
def pathToTextraData = "/home/user"
def myMobileNumber = "+357xxxxxxxx"

// Do not modify from here on
def sql = Sql.newInstance(
    "jdbc:sqlite:" + pathToTextraData + "messaging.db"
    )
def messages = sql.rows('SELECT m.*, c.lookup_key, c.display_name FROM messages AS m JOIN convos AS c ON m.convo_id=c._id')

System.out.withWriter('UTF-8') {w ->
  def xml = new MarkupBuilder(w)

    xml.mkp.xmlDeclaration(version: '1.0', encoding: 'UTF-8')
    xml.mkp.yieldUnescaped("<?xml-stylesheet type='text/xsl' href='sms.xsl'?>\n")

    xml.smses(count: messages.size()) {
      messages.each {
        sms(
            protocol: 0,
            address: it.lookup_key.replace('^', ''),
            date: it.ts,
            type: it.direction==1 ? 2 : 1,
            subject: 'null',
            body: it.text,
            toa: 'null',
            sc_toa: 'null',
            service_center: it.message_center_ts,
            read: '1',
            status: '0',
            readable_date: new Date(it.ts).toGMTString(),
            contact_name: it.display_name,
            locked: 0,
           )
      }
    }
}

