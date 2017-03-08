# textra_to_xml

Converts Textra **SMS** database to XML format readable by the application "SMS Backup & Restore"

_Copyright (C) Alexander Phinikarides, alexisph@gmail.com_

Instructions:

- Copy Textra database from Android to PC: `/data/data/com.textra/databases/messaging.db`
- Change `pathToTextraData` and `myMobileNumber` as required in `textra_to_xml.groovy`
- Run the script as `./textra_to_xml.groovy > sms.xml` or `groovy textra_to_xml.groovy > sms.xml`
 
The resulting `sms.xml` file can be transferred back to the Android device and imported with "SMS Backup & Restore".
UTF-8 characters and emoticons should be preserved.

_Inspired by the script listed in https://together.jolla.com/question/54249/how-to-saving-sms-text-conversations/?answer=102213#post-id-102213_
 
