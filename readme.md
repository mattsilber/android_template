Now in Kotlin.

### Use

Clone repo.

Rename to whatever.

Replace all instances of the package 'com.guardanis.blank' with your desired package name:

    grep -rl 'com.guardanis.blank' ./ | xargs sed -i 's/com.guardanis.blank/some.new.package/g'

Then rename the directories for 'com/guardanis/blank' to match your package:

    mv src/main/java/com/guardanis/blank/* src/main/java/some/new/package/

Import with AS.

Boom. Done.
