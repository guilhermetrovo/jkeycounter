# JKeyCounter

Do you use the numpad in your laptop's keyboard but for whatever reason you are getting a new laptop that does not have a numpad (against your will) and you want to prove how much you use/need/want that numpad?

This is the solution for your problem!

JKeyCounter is a Java Swing application that runs on your computer, listen and counts all numpad's keystrokes _globally_, whether you are on a browser, word processor, or any application!

## How?

All you need to do is open JKeyCounter, hit the Start button and type away anywhere!

If you are thinking _Java does not allow this, how is it possible?_, the answer is through Java Native Interface and a _DLL_, so yes, it only works in Windows currently.

# Compiling

```bash
mvn clean install
```

# Running

```bash
java -jar jKeyCounter-0.0.1.jar
```

# License

[MIT](https://choosealicense.com/licenses/mit/)
