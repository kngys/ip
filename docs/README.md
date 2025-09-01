# Serenity User Guide

![Ui](./Ui.png)

Serenity is a chatbot that helps you to manage your tasks in a simple and efficient manner.

## Adding a todo task

Adds a todo task to the task list.

Format: `todo DESCRIPTION`

Examples:
- `todo read book`
- `todo watch movie`

## Adding a deadline task

Adds a deadline task to the task list. 

Format: `deadline DESCRIPTION /by DATE`

- The `DATE` should be in the format `DD/MM/YYYY`

Examples: 
- `deadline return book /by 19/09/2024`
- `deadline write essay /by 20/10/2025`

## Adding an event task

Adds a deadline task to the task list.

Format: `deadline DESCRIPTION /from START /to END`

Examples:
- `event project meeting /from Monday 2pm /to 4pm`
- `event hackathon /from Tuesday /to Friday`

## Listing all tasks

Shows a list of all tasks in the task list.

Format: `list`

## Marking a task

Marks the task at the given index as done.

Format: `mark INDEX`

- The index refers to the index number shown in the task list
- The index must be a positive integer
- The index cannot be greater than the number of tasks in the task list

Example:
- `mark 1`

## Unmarking a task

Unmarks the task at the given index as done.

Format: `unmark INDEX`

- The index refers to the index number shown in the task list
- The index must be a positive integer
- The index cannot be greater than the number of tasks in the task list

Example:
- `unmark 1`

## Finding a task

Finds all tasks in the task list which contains the given keyword.

Format: `find KEYWORD`

- The search is case-sensitive e.g. `book` will not match `Book`
- Only description is searched

Example:
- `find book`
- `find write`

## Deleting a task
Deletes the task at the given index.

Format: `delete INDEX`

- The index refers to te index number shown in the task list
- The index must be a positive integer 
- The index cannot be greater than the number of tasks in the task list

Example:
- `delete 1`

## Updating a task

Updates the task at the given index

Format: `update INDEX /update TASK_TYPE DESCRIPTION`

- The task type is either `todo`, `deadline`, or `event`
- The description required is dependent on the task type  

Examples:
- `update 1 /update todo read mystery book`
- `update 2 /update deadline return book /by 22/09/2024`
- `update 3 /update event project meeting /from Monday 3pm /to 5pm`

## Exiting chatbot 

Displays goodbye message and exits chatbot.

Format: `bye`












