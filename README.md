Todo application made with Quarkus, Kotlin, HTMX and Tailwind.

## How to develop

First, create css file with tailwindcss:

```bash 
npx tailwindcss -i ./src/main/resources/templates/css/input.css -o ./src/main/resources/META-INF/resources/css/output.css --watch
```

Then run the application:

```bash
quarkus dev
```