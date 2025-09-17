# Mumbo Task Chatbot - User Guide

Mumbo is a friendly, task-focused chatbot that helps you capture todos, deadlines, and events while keeping everything organised in a single list. It runs with a JavaFX interface and stores your tasks locally so you can pick up where you left off.

## Prerequisites
- JDK 17 (set as both the project SDK and language level)
- Gradle (already bundled via the provided `gradlew` scripts)
- JavaFX runtime (fetched automatically through Gradle dependencies)

## Quick Start
1. Clone or download this repository.
2. From the project root, build and run Mumbo using one of the following options:
   - `./gradlew run` (macOS/Linux) or `gradlew.bat run` (Windows) to start the JavaFX app directly.
   - `./gradlew shadowJar` to produce `build/libs/mumbo.jar`, then launch it with `java -jar build/libs/mumbo.jar`.
3. A window titled "Mumbo" will appear with a scrolling chat pane. Type into the input box and press **Enter** or click **Send** to converse with the assistant.

## Chat Flow
- Mumbo greets you with a welcome message and awaits commands.
- Each message you send is echoed on the right; Mumbo replies on the left.
- Tasks are saved automatically after every change. Restarting the app reloads your list from the previous session.

<h2>Command Reference</h2>

<table>
  <thead>
    <tr>
      <th>Command</th>
      <th>Format</th>
      <th>Example</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>list</code></td>
      <td><code>list</code></td>
      <td><code>list</code></td>
      <td>Show every task with its index and status.</td>
    </tr>
    <tr>
      <td><code>todo</code></td>
      <td><code>todo &lt;description&gt;</code></td>
      <td><code>todo Read Effective Java</code></td>
      <td>Add a basic task with no date attached.</td>
    </tr>
    <tr>
      <td><code>deadline</code></td>
      <td><code>deadline &lt;description&gt; /by &lt;date&gt;</code></td>
      <td><code>deadline Submit report /by 2024/09/30 17:00</code></td>
      <td>Add a task that is due on a specific date/time.</td>
    </tr>
    <tr>
      <td><code>event</code></td>
      <td><code>event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;</code></td>
      <td><code>event Team sync /from 15/05/2024 09:00 /to 15/05/2024 10:00</code></td>
      <td>Schedule an event with a start and end.</td>
    </tr>
    <tr>
      <td><code>mark</code></td>
      <td><code>mark &lt;index&gt;</code></td>
      <td><code>mark 2</code></td>
      <td>Mark the indexed task as done.</td>
    </tr>
    <tr>
      <td><code>unmark</code></td>
      <td><code>unmark &lt;index&gt;</code></td>
      <td><code>unmark 2</code></td>
      <td>Mark the indexed task as not done.</td>
    </tr>
    <tr>
      <td><code>delete</code></td>
      <td><code>delete &lt;index&gt;</code></td>
      <td><code>delete 4</code></td>
      <td>Remove the indexed task permanently.</td>
    </tr>
    <tr>
      <td><code>tag</code></td>
      <td><code>tag &lt;index&gt; &lt;tag&gt;</code></td>
      <td><code>tag 3 urgent</code></td>
      <td>Apply or replace a single tag on that task.</td>
    </tr>
    <tr>
      <td><code>find</code></td>
      <td><code>find &lt;keyword&gt;</code></td>
      <td><code>find report</code></td>
      <td>List tasks whose descriptions contain the keyword (case-insensitive).</td>
    </tr>
    <tr>
      <td><code>findtag</code></td>
      <td><code>findtag &lt;tag&gt;</code></td>
      <td><code>findtag urgent</code></td>
      <td>List tasks whose tag matches the query (case-insensitive).</td>
    </tr>
    <tr>
      <td><code>clear</code></td>
      <td><code>clear</code></td>
      <td><code>clear</code></td>
      <td>Empty the entire task list immediately.</td>
    </tr>
    <tr>
      <td><code>help</code></td>
      <td><code>help</code></td>
      <td><code>help</code></td>
      <td>Display the built-in command summary.</td>
    </tr>
    <tr>
      <td><code>bye</code></td>
      <td><code>bye</code></td>
      <td><code>bye</code></td>
      <td>Initiate shutdown. If tasks remain, you will be asked whether to clear them first.</td>
    </tr>
  </tbody>
</table>


## Date and Time Formats
Mumbo accepts the following date/time patterns (24-hour clock):
- `yyyy/MM/dd` (e.g. `2024/05/12`)
- `yyyy/MM/dd HH:mm` (e.g. `2024/05/12 14:30`)
- `dd/MM/yyyy` (e.g. `12/05/2024`)
- `dd/MM/yyyy HH:mm` (e.g. `12/05/2024 14:30`)
Dates entered without a time are assumed to start at midnight.

## Working with Tags
- Each task can hold at most one tag; issuing another `tag` command replaces the previous value.
- Tags are free-form single words. Use `findtag` to search for tasks that share a tag.

## Data Storage
- Tasks are stored in `data/mumbo-tasks.txt` in the application directory.
- The file is created automatically on first launch. Deleting it resets your list.
- Storage uses a human-readable pipe-separated format (one line per task).

## Exiting the App
- Typing `bye` closes the assistant. If your list is not empty, Mumbo will prompt: `Would you care for me to clear your tasks before you take your leave?`
- Reply with `yes`/`y` to erase all tasks before exiting, or `no`/`n` to keep them.
- You can also close the window manually; any tasks saved before closure remain intact.

## Troubleshooting
- **Unrecognised command**: Type `help` to see every supported action and double-check spacing (`/by`, `/from`, `/to`).
- **Date rejected**: Ensure it matches one of the accepted patterns above.
- **Out-of-range index**: Run `list` first, then use the number shown to `mark`, `unmark`, `delete`, or `tag`.
- **User interface does not launch**: Confirm you are using JDK 17 and have run the app via Gradle so JavaFX dependencies are resolved.

Enjoy your productivity with Mumbo!
