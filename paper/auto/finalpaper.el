(TeX-add-style-hook
 "finalpaper"
 (lambda ()
   (TeX-add-to-alist 'LaTeX-provided-class-options
                     '(("article" "13pt")))
   (TeX-add-to-alist 'LaTeX-provided-package-options
                     '(("geometry" "margin=1in" "top=0.5in" "footskip=0.25in")))
   (TeX-run-style-hooks
    "latex2e"
    "article"
    "art10"
    "tikz"
    "amssymb"
    "amsmath"
    "geometry")
   (TeX-add-symbols
    '("be" 1)
    '("zzd" 1)
    '("D" 2)
    '("B" 1)
    '("txt" 1)
    '("fp" 2)
    '("f" 2)
    '("Mod" 1)
    "then"
    "tand"
    "tor"
    "done"
    "zz"
    "N"
    "R"
    "C"
    "zzz"))
 :latex)

