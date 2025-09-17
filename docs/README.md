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

## Command Reference
| Command | Format | Example | Description |
| --- | --- | --- | --- |
| `list` | `list` | `list` | Show every task with its index and status. |
| `todo` | `todo <description>` | `todo Read Effective Java` | Add a basic task with no date attached. |
| `deadline` | `deadline <description> /by <date>` | `deadline Submit report /by 2024/09/30 17:00` | Add a task that is due on a specific date/time. |
| `event` | `event <description> /from <start> /to <end>` | `event Team sync /from 15/05/2024 09:00 /to 15/05/2024 10:00` | Schedule an event with a start and end. |
| `mark` | `mark <index>` | `mark 2` | Mark the indexed task as done. |
| `unmark` | `unmark <index>` | `unmark 2` | Mark the indexed task as not done. |
| `delete` | `delete <index>` | `delete 4` | Remove the indexed task permanently. |
| `tag` | `tag <index> <tag>` | `tag 3 urgent` | Apply or replace a single tag on that task. |
| `find` | `find <keyword>` | `find report` | List tasks whose descriptions contain the keyword (case-insensitive). |
| `findtag` | `findtag <tag>` | `findtag urgent` | List tasks whose tag matches the query (case-insensitive). |
| `clear` | `clear` | `clear` | Empty the entire task list immediately. |
| `help` | `help` | `help` | Display the built-in command summary. |
| `bye` | `bye` | `bye` | Initiate shutdown. If tasks remain, you will be asked whether to clear them first. |

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
