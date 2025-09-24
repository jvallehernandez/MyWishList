# Android Project 2 - Wishlist

Submitted by: **Jose Valle**

**Wishlist** is a wishlist app that helps the user keep track of what they want to buy.

Time spent: **10** hours in total

## Required Features
The following **required** functionality is completed:

- [x] User can add an item to their wishlist (Name, Price, URL)
- [x] User can see their list of items based on previously inputted items (scrollable list)

## Optional / Stretch Features
- [ ] Wishlist app is 🎨 **customized** 🎨 (custom theme)
- [x] User can delete an item by long pressing on the item
- [x] User can open an item's URL by clicking on the item

## Additional Features
- [x] Input validation (name required, price must be a number, URL format checked)
- [x] URL normalization (adds `http://` if scheme missing)
- [x] Auto-scroll to newly added item
- [x] Clears inputs and hides keyboard after submit
- [x] Uses `MaterialCardView` row with `wrap_content` height to ensure items render correctly
- [x] Graceful error handling for invalid URLs (`ActivityNotFoundException`)

## Video Walkthrough
Here's a walkthrough of implemented user stories:

<img src="wishlist.gif" title="Video Walkthrough" width="300" alt="Video Walkthrough" />

GIF created with [Kap](https://getkap.co/)

## Notes
- Ran into an AAPT error when building `item_wishlist.xml`—fixed by adding the `tools` XML namespace for `tools:text`.
- Initial `R` reference issue caused by wrong import / resource id mismatch; resolved by removing `import android.R` and using the correct layout/id names.
- Avoided constraining views across different parents (RecyclerView vs AppBar); simplified to a single `ConstraintLayout`.

## License

Copyright 2025 Jose Valle

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
