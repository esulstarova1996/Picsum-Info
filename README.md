# Description

Multi-module app that gets data from Picsum Photo api.

Containins 2 main view:
   
  1. Infinity Scroll List for the images
  2. Details Screen

## Module design

| Module  name      | Type                 | Description                                     |
|-------------------|----------------------|-------------------------------------------------|
|        app        | Android  Application | MainActivity, BaseApplication, Main navigaition |
| photo-datasource  | Java/Kotlin  Library | Data-soruces (network only) for photo module    |
| photo-domain      | Java/Kotlin Library  | Domain model for photo Module                   |
| photo-interactors | Java/Kotlin  Library | Use-cases for photo Module                      |
| ui_photoList      | Android Library      | View screen for infinity scroll list images     |
| ui_photoDetails   | Android Library      | View screen for detail                          |


## Tests
Unit Test for every use-case
  1. GetFilteredImageUrl
  2. GetPhotoDetail
  3. GetPhotoPage

## API 

For the list https://picsum.photos/v2/list

Details https://picsum.photos/id/0/info

Image filtering https://picsum.photos/id/870/200/300?grayscale&blur=2
