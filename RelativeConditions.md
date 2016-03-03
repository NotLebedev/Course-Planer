# RelativelyResizable
* `resize` should call all childrens' resizes
* `setRelativeSize` should call `relayout`

# RelativelyLocated
* 'relocate' should call all childrens' relocates

# RelativelyLayouted
* Should call 'relayout' in constructor
* Should call 'super.relayout'
* Should not change the size of self

# CanSizeSelf
* Should not set location of any elements
* Should call 'super.sizeSelf'
