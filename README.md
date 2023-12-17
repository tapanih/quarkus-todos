First create css file with tailwindcss:

```bash 
npx tailwindcss -i ./src/main/resources/templates/css/input.css -o ./src/main/resources/META-INF/resources/css/output.css
```

Then run the application:

```bash
quarkus dev
```